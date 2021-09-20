package ru.jsavings.domain.usecase.database.transaction

import io.reactivex.rxjava3.core.Completable
import ru.jsavings.data.entity.database.AccountEntity
import ru.jsavings.data.entity.database.TransactionGroupEntity
import ru.jsavings.data.repository.database.account.AccountRepository
import ru.jsavings.data.repository.database.transaction.TransactionRepository
import ru.jsavings.data.repository.database.wallet.WalletRepository
import ru.jsavings.domain.model.database.category.TransactionCategoryType
import ru.jsavings.domain.model.database.transaction.Transaction
import ru.jsavings.domain.usecase.common.BaseUseCase

/**
 * Delete transaction from database by id usecase
 * @param transactionRepository [TransactionRepository] to interact with
 * @param walletRepository [WalletRepository] to update wallets balance
 * @param accountRepository [AccountRepository] to update account balance
 *
 * @author JustSpace
 */
class DeleteTransactionByIdUseCase(
	private val transactionRepository: TransactionRepository,
	private val walletRepository: WalletRepository,
	private val accountRepository: AccountRepository,
) : BaseUseCase() {

	private fun getUpdateCompletable(
		accountEntity: AccountEntity,
		transactionGroupEntity: TransactionGroupEntity
	): Completable {
		val category = transactionGroupEntity.categoryEntity
		if (category == null) {
			val fromWalletEntity = transactionGroupEntity.fromWalletEntity!!
			val toWalletEntity = transactionGroupEntity.toWalletEntity!!

			if (fromWalletEntity.walletId == toWalletEntity.walletId) return Completable.complete()

			val newFromWalletBalance = fromWalletEntity.balance +
					transactionGroupEntity.transactionEntity.sumInWalletCurrency
			val newToWalletBalance = toWalletEntity.balance - transactionGroupEntity.transactionEntity.transferSum!!

			return Completable.mergeArray(
				walletRepository.updateWallet(fromWalletEntity.copy(balance = newFromWalletBalance)),
				walletRepository.updateWallet(toWalletEntity.copy(balance = newToWalletBalance))
			)
		}

		val (walletEntity, newWalletBalance, newAccountBalance) =
			if (category.type == TransactionCategoryType.INCOME.toString()) {
				val wallet = transactionGroupEntity.toWalletEntity!!
				Triple(
					wallet,
					wallet.balance - transactionGroupEntity.transactionEntity.sumInWalletCurrency,
					accountEntity.balanceInMainCurrency - transactionGroupEntity.transactionEntity.sumInAccountCurrency
				)
			} else {
				val wallet = transactionGroupEntity.fromWalletEntity!!
				Triple(
					wallet,
					wallet.balance + transactionGroupEntity.transactionEntity.sumInWalletCurrency,
					accountEntity.balanceInMainCurrency + transactionGroupEntity.transactionEntity.sumInAccountCurrency
				)
			}

		return Completable.mergeArray(
			walletRepository.updateWallet(walletEntity.copy(balance = newWalletBalance)),
			accountRepository.updateAccount(accountEntity.copy(balanceInMainCurrency = newAccountBalance))
		)
	}

	/**
	 * Invokes usecase
	 * @param transactionId Id of transaction that must be deleted
	 * @throws NullPointerException if [Transaction.category] is null AND ([Transaction.fromWallet] is null OR [Transaction.toWallet] is null) OR
	 * [TransactionCategoryType] is [TransactionCategoryType.INCOME] AND [Transaction.toWallet] is null OR
	 * [TransactionCategoryType] is [TransactionCategoryType.CONSUMPTION] AND [Transaction.fromWallet] is null
	 * @return [Completable] source of action
	 *
	 * @author JustSpace
	 */
	operator fun invoke(transactionId: Long): Completable = transactionRepository.getTransactionById(transactionId)
		.flatMapCompletable { transactionGroupEntity ->
			accountRepository.getAccountByIdSingle(transactionGroupEntity.transactionEntity.accountFkId)
				.flatMapCompletable { accountEntity -> Completable.mergeArray(
					transactionRepository.deleteTransactionById(transactionId),
					getUpdateCompletable(accountEntity, transactionGroupEntity)
				)}
		}
}
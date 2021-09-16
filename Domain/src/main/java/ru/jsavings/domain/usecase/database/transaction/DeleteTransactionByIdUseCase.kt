package ru.jsavings.domain.usecase.database.transaction

import io.reactivex.rxjava3.core.Completable
import ru.jsavings.data.entity.database.AccountEntity
import ru.jsavings.data.entity.database.TransactionGroupEntity
import ru.jsavings.data.repository.database.account.AccountRepository
import ru.jsavings.data.repository.database.transaction.TransactionRepository
import ru.jsavings.data.repository.database.wallet.WalletRepository
import ru.jsavings.domain.mappers.database.AccountMapper
import ru.jsavings.domain.model.database.category.TransactionCategoryType
import ru.jsavings.domain.model.database.transaction.Transaction
import ru.jsavings.domain.usecase.common.BaseUseCase

/**
 * Delete transaction from database by id usecase
 * @param transactionRepository [TransactionRepository] to interact with
 *
 * @author JustSpace
 */
class DeleteTransactionByIdUseCase(
	private val transactionRepository: TransactionRepository,
	private val walletRepository: WalletRepository,
	private val accountRepository: AccountRepository,
	private val accountMapper: AccountMapper
) : BaseUseCase {

	private fun getUpdateCompletable(
		accountEntity: AccountEntity,
		transactionGroupEntity: TransactionGroupEntity
	): Completable {
		val category = transactionGroupEntity.categoryEntity
		if (category == null) {
			val fromWalletEntity = transactionGroupEntity.fromWalletEntity!!
			val toWalletEntity = transactionGroupEntity.toWalletEntity!!

			if (fromWalletEntity.walletName == toWalletEntity.walletName) return Completable.complete()

			val transferSum = transactionGroupEntity.transactionEntity.transferSum!!

			val newFromWalletBalance = fromWalletEntity.balance +
					transactionGroupEntity.transactionEntity.sumInWalletCurrency
			val newToWalletBalance = toWalletEntity.balance - transferSum

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
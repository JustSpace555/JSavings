package ru.jsavings.domain.usecase.database.transaction

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.repository.database.account.AccountRepository
import ru.jsavings.data.repository.database.transaction.TransactionRepository
import ru.jsavings.data.repository.database.wallet.WalletRepository
import ru.jsavings.data.repository.network.currency.CurrencyRepository
import ru.jsavings.domain.mappers.database.TransactionMapper
import ru.jsavings.domain.mappers.database.WalletMapper
import ru.jsavings.domain.model.database.category.TransactionCategoryType
import ru.jsavings.domain.model.database.transaction.Transaction
import ru.jsavings.domain.usecase.common.BaseUseCase

/**
 * Save new transaction in database usecase
 * @param accountRepository [AccountRepository] to update account balance
 * @param walletRepository [WalletRepository] to update wallets balance in database
 * @param transactionRepository [TransactionRepository] to interact with transaction's database table
 * @param currencyRepository [CurrencyRepository] to get conversion between currencies if needed
 * @param walletMapper [WalletMapper] to map model to entity
 * @param transactionMapper [TransactionMapper] to map model to entity
 *
 * @author JustSpace
 */
class SaveNewTransactionUseCase(
	private val accountRepository: AccountRepository,
	private val walletRepository: WalletRepository,
	private val transactionRepository: TransactionRepository,
	private val currencyRepository: CurrencyRepository,
	private val walletMapper: WalletMapper,
	private val transactionMapper: TransactionMapper,
) : BaseUseCase() {

	private fun Transaction.getWallet(type: TransactionCategoryType) = if (type == TransactionCategoryType.INCOME)
		toWallet!!
	else
		fromWallet!!

	private fun Transaction.getWalletWithNewBalance(type: TransactionCategoryType) = walletMapper.mapModelToEntity(
		if (type == TransactionCategoryType.INCOME) {
			val oldBalance = toWallet!!.balance
			toWallet.copy(balance = oldBalance + sumInWalletCurrency)
		} else {
			val oldBalance = fromWallet!!.balance
			fromWallet.copy(balance = oldBalance - sumInWalletCurrency)
		}
	)

	/**
	 * Invokes usecase
	 * @param newTransaction [Transaction] to insert in database
	 * @throws NullPointerException if [TransactionCategoryType.INCOME] and [Transaction.toWallet] is null or
	 * [TransactionCategoryType.CONSUMPTION] and [Transaction.fromWallet] is null or
	 * [TransactionCategoryType.TRANSFER] and [Transaction.toWallet] is null and [Transaction.fromWallet] is null
	 * @return [Single] source with id of new transaction
	 *
	 * @author JustSpace
	 */
	operator fun invoke(newTransaction: Transaction): Single<Long> = if (newTransaction.category == null) {
		getConversion(
			currencyRepository,
			fromCurrency = newTransaction.fromWallet!!.currency,
			toCurrency = newTransaction.toWallet!!.currency,
			amount = newTransaction.sumInWalletCurrency
		).flatMap { conversionResult ->
			if (newTransaction.fromWallet.walletId == newTransaction.toWallet.walletId) {
				Completable.complete()
			} else {
				val newFromWalletBalance = newTransaction.fromWallet.balance - newTransaction.sumInWalletCurrency
				val fromWalletCopy = newTransaction.fromWallet.copy(balance = newFromWalletBalance)

				val newToWalletBalance = newTransaction.toWallet.balance + conversionResult
				val toWalletCopy = newTransaction.toWallet.copy(balance = newToWalletBalance)
					Completable.mergeArray(
						walletRepository.updateWallet(walletMapper.mapModelToEntity(fromWalletCopy)),
						walletRepository.updateWallet(walletMapper.mapModelToEntity(toWalletCopy))
					)
			}.andThen(Single.defer { Single.just(conversionResult) })
		}.flatMap { conversionResult ->
			val transactionCopy = newTransaction.copy(transferSum = conversionResult, sumInAccountCurrency = 0.0)
			transactionRepository.insertNewTransaction(transactionMapper.mapModelToTransactionEntity(transactionCopy))
		}
	} else {
		val type = newTransaction.category.categoryType

		accountRepository.getAccountByIdSingle(newTransaction.accountId)
			.flatMap { accountEntity ->
				getConversion(
					currencyRepository,
					fromCurrency = newTransaction.getWallet(type).currency,
					toCurrency = accountEntity.mainCurrencyCode,
					amount = newTransaction.sumInWalletCurrency
				).map { conversionResult ->
					val newAccountBalance = accountEntity.balanceInMainCurrency +
							if (type == TransactionCategoryType.INCOME) conversionResult else -conversionResult
					val accountCopy = accountEntity.copy(balanceInMainCurrency = newAccountBalance)
					Pair(accountCopy, conversionResult)
				}
			}.flatMap { pair ->
				Completable.mergeArray(
					accountRepository.updateAccount(pair.first),
					walletRepository.updateWallet(newTransaction.getWalletWithNewBalance(type))
				).andThen(Single.defer { Single.just(pair.second) })
			}.flatMap { conversionResult ->
				val transactionCopy = newTransaction.copy(sumInAccountCurrency = conversionResult)
				transactionRepository.insertNewTransaction(transactionMapper.mapModelToTransactionEntity(transactionCopy))
			}
	}
}
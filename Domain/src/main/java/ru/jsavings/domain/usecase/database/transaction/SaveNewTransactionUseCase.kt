package ru.jsavings.domain.usecase.database.transaction

import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.entity.database.AccountEntity
import ru.jsavings.data.repository.database.account.AccountRepository
import ru.jsavings.data.repository.database.transaction.TransactionRepository
import ru.jsavings.data.repository.database.wallet.WalletRepository
import ru.jsavings.data.repository.network.currency.CurrencyRepository
import ru.jsavings.domain.mappers.database.TransactionMapper
import ru.jsavings.domain.mappers.database.WalletMapper
import ru.jsavings.domain.model.database.transaction.Transaction
import ru.jsavings.domain.model.database.category.TransactionCategoryType
import ru.jsavings.domain.usecase.common.BaseUseCase
import kotlin.properties.Delegates

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
) : BaseUseCase {

	private fun getConversion(fromCurrencyCode: String, toCurrencyCode: String, amount: Double) =
		if (fromCurrencyCode != toCurrencyCode)
			currencyRepository.getConversion(
				from = fromCurrencyCode,
				to = toCurrencyCode,
				amount = amount,
				precision = 2
			).map { it.result }
		else
			Single.just(amount)

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
		var transferSum by Delegates.notNull<Double>()

		walletRepository.updateWallet(walletMapper.mapModelToEntity(
			newTransaction.fromWallet!!.copy(
				balance = newTransaction.fromWallet.balance - newTransaction.sumInWalletCurrency
			)
		)).andThen(getConversion(
			newTransaction.fromWallet.currency,
			newTransaction.toWallet!!.currency,
			newTransaction.sumInWalletCurrency
		)).flatMapCompletable { conversionResult ->
			transferSum = conversionResult
			val newBalance = newTransaction.toWallet.balance + conversionResult
			walletRepository.updateWallet(
				walletMapper.mapModelToEntity(newTransaction.toWallet.copy(balance = newBalance))
			)
		}.andThen(Single.defer {
			val transactionCopy = newTransaction.copy(transferSum = transferSum, sumInAccountCurrency = 0.0)
			transactionRepository.insertNewTransaction(transactionMapper.mapModelToEntity(transactionCopy))
		})
	} else {
		lateinit var account: AccountEntity
		var sumInAccountCurrency by Delegates.notNull<Double>()
		val type = newTransaction.category.categoryType

		accountRepository.getAccountById(newTransaction.accountId)
			.flatMap { accountEntity ->
				account = accountEntity
				getConversion(
					newTransaction.getWallet(type).currency,
					accountEntity.mainCurrencyCode,
					newTransaction.sumInWalletCurrency
				)
			}
			.flatMapCompletable { conversionResult ->
				sumInAccountCurrency = conversionResult
				val newBalance = if (type == TransactionCategoryType.INCOME)
					account.balanceInMainCurrency + conversionResult
				else
					account.balanceInMainCurrency - conversionResult

				accountRepository.updateAccount(account.copy(balanceInMainCurrency = newBalance))
			}
			.andThen(walletRepository.updateWallet(newTransaction.getWalletWithNewBalance(type)))
			.andThen(Single.defer {
				val transactionCopy = newTransaction.copy(sumInAccountCurrency = sumInAccountCurrency)
				transactionRepository.insertNewTransaction(transactionMapper.mapModelToEntity(transactionCopy))
			})
	}
}
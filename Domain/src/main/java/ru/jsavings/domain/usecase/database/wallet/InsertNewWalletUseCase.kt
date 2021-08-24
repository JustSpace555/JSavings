package ru.jsavings.domain.usecase.database.wallet

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.entity.database.AccountEntity
import ru.jsavings.data.repository.database.account.AccountRepository
import ru.jsavings.data.repository.database.wallet.WalletRepository
import ru.jsavings.data.repository.network.currency.CurrencyRepository
import ru.jsavings.domain.mappers.database.WalletMapper
import ru.jsavings.domain.model.database.wallet.Wallet
import ru.jsavings.domain.usecase.common.BaseUseCase

/**
 * Use case for adding new wallet to database
 * @param walletRepository [WalletRepository] to interact with wallets' database table
 * @param accountRepository [AccountRepository] to update wallet's account balance
 * @param currencyRepository [CurrencyRepository] to convert balance from wallet to account's currency
 * @param mapper [WalletMapper] to map model to entity
 *
 * @author JustSpace
 */
class InsertNewWalletUseCase(
	private val walletRepository: WalletRepository,
	private val accountRepository: AccountRepository,
	private val currencyRepository: CurrencyRepository,
	private val mapper: WalletMapper
) : BaseUseCase {

	private fun getConversion(from: String, to: String, amount: Double): Single<Double> =
		if (from != to)
			currencyRepository.getConversion(from, to, amount, 2).map { it.result }
		else
			Single.just(amount)

	/**
	 * Executing usecase
	 * @param wallet [Wallet] model to insert
	 * @return [Completable] source of action
	 *
	 * @author JustSpace
	 */
	operator fun invoke(wallet: Wallet): Completable {
		lateinit var accountEntity: AccountEntity

		return walletRepository
			.insertNewWallet(mapper.mapModelToEntity(wallet))
			.flatMap { accountRepository.getAccountById(wallet.accountId) }
			.flatMap { account ->
				accountEntity = account
				getConversion(wallet.currency, account.mainCurrencyCode, wallet.balance)
			}.flatMapCompletable { conversionResult ->
				val startingBalance = accountEntity.balanceInMainCurrency
				accountRepository.updateAccount(
					accountEntity.copy(balanceInMainCurrency = startingBalance + conversionResult)
				)
			}
	}
}
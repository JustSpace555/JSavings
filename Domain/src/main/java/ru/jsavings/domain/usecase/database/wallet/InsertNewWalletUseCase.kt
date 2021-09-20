package ru.jsavings.domain.usecase.database.wallet

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
) : BaseUseCase() {

	/**
	 * Executing usecase
	 * @param wallet [Wallet] model to insert
	 * @return [Single] source of action with new wallet id in database
	 *
	 * @author JustSpace
	 */
	operator fun invoke(wallet: Wallet): Single<Long> {
		lateinit var accountEntity: AccountEntity

		return accountRepository.getAccountByIdSingle(wallet.accountId)
			.flatMap { account ->
				accountEntity = account
				getConversion(
					currencyRepository,
					fromCurrency = wallet.currency,
					toCurrency = account.mainCurrencyCode,
					amount = wallet.balance
				)
			}.flatMapCompletable { conversionResult ->
				val oldBalance = accountEntity.balanceInMainCurrency
				accountRepository.updateAccount(accountEntity.copy(balanceInMainCurrency = oldBalance + conversionResult))
			}.andThen(walletRepository.insertNewWallet(mapper.mapModelToEntity(wallet)))
	}
}
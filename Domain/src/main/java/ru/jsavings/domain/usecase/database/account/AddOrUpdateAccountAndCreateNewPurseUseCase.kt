package ru.jsavings.domain.usecase.database.account

import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.model.database.Account
import ru.jsavings.data.model.database.purse.Purse
import ru.jsavings.data.repository.database.account.AccountRepository
import ru.jsavings.data.repository.database.purse.PurseRepository
import ru.jsavings.data.repository.network.currency.CurrencyRepository
import ru.jsavings.domain.usecase.common.SingleUseCase

class AddOrUpdateAccountAndCreateNewPurseUseCase(
	override val repository: AccountRepository,
	private val purseRepository: PurseRepository,
	private val currencyRepository: CurrencyRepository,
) : SingleUseCase<Long, Pair<Account, Purse>>() {

	private fun getConversion(
		accountCurrencyCode: String,
		purseCurrencyCode: String,
		amount: Double,
	) = if (accountCurrencyCode != purseCurrencyCode)
		currencyRepository.getConversion(
			from = purseCurrencyCode,
			to = accountCurrencyCode,
			amount = amount,
			precision = 2
		).map { it.result }
	else Single.just(amount)

	override fun buildSingleUseCase(params: Pair<Account, Purse>): Single<Long> {
		var account = params.first
		val purse = params.second

		return if (account.accountId == 0L)
				getConversion(
					account.mainCurrencyCode,
					purse.currency,
					purse.balance
				).flatMap {
					account = account.copy(balanceInMainCurrency = it)
					repository.createNewAccount(account)
				}.flatMap { newAccountId ->
					account = account.copy(accountId = newAccountId)
					purseRepository.addNewPurse(purse.copy(account = account))
				}.map {
					account.accountId
				}
		else
			repository.getAccountById(account.accountId).flatMap {
				account = account.copy(
					name = it.name,
					mainCurrencyCode = it.mainCurrencyCode,
					balanceInMainCurrency = it.balanceInMainCurrency
				)
				getConversion(it.mainCurrencyCode, purse.currency, purse.balance)
			}.flatMapCompletable {
				val startBalance = account.balanceInMainCurrency
				account = account.copy(balanceInMainCurrency = startBalance + it)
				repository.updateAccount(account)
			}.andThen {
				purseRepository.addNewPurse(purse.copy(account = account))
			}.toSingle {
				account.accountId
			}
	}
}
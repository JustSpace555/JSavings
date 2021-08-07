package ru.jsavings.domain.usecase.network

import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.model.network.currency.Currency
import ru.jsavings.data.repository.network.currency.CurrencyRepository
import ru.jsavings.domain.usecase.common.SingleUseCase

class GetCurrenciesUseCase(
	override val repository: CurrencyRepository
) : SingleUseCase<List<Currency>, Unit>() {

	private val availableCurrencies = java.util.Currency.getAvailableCurrencies()

	override fun buildSingleUseCase(params: Unit): Single<List<Currency>> =
		repository.getAvailableCurrencyList().map { myCurrenciesList ->

			val myCurrenciesCodes = myCurrenciesList.map { it.code.uppercase() }

			availableCurrencies.asSequence()
				.filter { currency ->
					myCurrenciesCodes.contains(currency.currencyCode.uppercase())
				}.map { newCurrency ->
					Currency(
						newCurrency.currencyCode,
						newCurrency.displayName,
						newCurrency.symbol
					)
				}.toList()
		}
}
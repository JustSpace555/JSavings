package ru.jsavings.domain.usecase.network

import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.repository.network.currency.CurrencyRepository
import ru.jsavings.domain.model.network.currency.Currency
import ru.jsavings.domain.usecase.common.BaseUseCase

/**
 * Use case to get all currencies from api and [java.util.Currency]
 * @param repository [CurrencyRepository] to interact with api
 *
 * @author JustSpace
 */
class GetCurrenciesUseCase(private val repository: CurrencyRepository) : BaseUseCase() {

	private val availableCurrenciesCodes = java.util.Currency.getAvailableCurrencies().map { it.currencyCode }

	/**
	 * Execute usecase
	 * @return [Single] source of [Currency] from api matched with [java.util.Currency]
	 *
	 * @author JustSpace
	 */
	operator fun invoke(): Single<List<Currency>> = repository.getAvailableCurrencies().map { currencyEntity ->
		availableCurrenciesCodes.intersect(currencyEntity.symbols.keys).map { currencyCode ->
			val newCurrency = java.util.Currency.getInstance(currencyCode)
			Currency(
				code = newCurrency.currencyCode,
				name = newCurrency.displayName,
				symbol = newCurrency.symbol
			)
		}.sortedBy { it.code }
	}
}
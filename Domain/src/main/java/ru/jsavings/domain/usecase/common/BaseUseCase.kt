package ru.jsavings.domain.usecase.common

import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.repository.network.currency.CurrencyRepository

/**
 * Base interface for all usecases in app
 *
 * @author JustSpace
 */
abstract class BaseUseCase {

	protected fun getConversion(
		currencyRepository: CurrencyRepository,
		fromCurrency: String,
		toCurrency: String,
		amount: Double,
		precision: Int = 2
	): Single<Double> = if (fromCurrency != toCurrency) {
		currencyRepository.getConversion(
			from = fromCurrency,
			to = toCurrency,
			amount = amount,
			precision = precision
		).map { it.result }
	} else {
		Single.just(amount)
	}
}
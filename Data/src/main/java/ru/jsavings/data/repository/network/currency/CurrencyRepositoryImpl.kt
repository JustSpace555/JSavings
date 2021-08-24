package ru.jsavings.data.repository.network.currency

import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.entity.network.ConversionEntity
import ru.jsavings.data.entity.network.CurrencyEntity
import ru.jsavings.data.network.api.exchangerate.ExchangeRateApi

/**
 * Implementation of [CurrencyRepository]
 * @param api [ExchangeRateApi] to get data from
 *
 * @author JustSpace
 */
internal class CurrencyRepositoryImpl(override val api: ExchangeRateApi) : CurrencyRepository {

	override fun getAvailableCurrencies(): Single<CurrencyEntity> = api.getCurrencies()

	override fun getConversion(
		from: String,
		to: String,
		amount: Double,
		precision: Int
	): Single<ConversionEntity> = api.getConversion(from, to, amount, precision)
}
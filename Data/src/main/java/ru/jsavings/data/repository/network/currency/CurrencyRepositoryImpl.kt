package ru.jsavings.data.repository.network.currency

import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.mappers.network.ConversionMapper
import ru.jsavings.data.mappers.network.CurrencyRequestMapper
import ru.jsavings.data.model.network.ConversionInfo
import ru.jsavings.data.model.network.currency.Currency
import ru.jsavings.data.network.api.exchangerate.ExchangeRateApi

internal class CurrencyRepositoryImpl(
	private val api: ExchangeRateApi,
	private val currencyRequestMapper: CurrencyRequestMapper,
	private val conversionMapper: ConversionMapper
) : CurrencyRepository {

	override fun getAvailableCurrencyList(): Single<List<Currency>> =
		api.getCurrencyList().map { currencyRequestMapper.mapEntityToModel(it) }

	override fun getConversion(
		from: String,
		to: String,
		amount: Double,
		precision: Int
	): Single<ConversionInfo> = api.getConversion(from, to, amount, precision).map {
		conversionMapper.mapEntityToModel(it)
	}
}
package ru.jsavings.data.repository.network.currency

import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.model.network.ConversionInfo
import ru.jsavings.data.model.network.currency.Currency
import ru.jsavings.data.repository.common.BaseRepository

interface CurrencyRepository : BaseRepository {

	fun getAvailableCurrencyList(): Single<List<Currency>>

	fun getConversion(
		from: String,
		to: String,
		amount: Double,
		precision: Int,
	): Single<ConversionInfo>
}
package ru.jsavings.data.network.api.exchangerate

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.jsavings.data.entity.network.ConversionEntity
import ru.jsavings.data.entity.network.crypto.CryptoCoinEntity
import ru.jsavings.data.entity.network.currency.CurrencyEntity
import ru.jsavings.data.network.common.BaseApi

internal interface ExchangeRateApi : BaseApi {

	@GET("symbols")
	fun getCurrencyList(): Single<CurrencyEntity>

	@GET("cryptocurrencies")
	fun getCryptoCoinsList(): Single<CryptoCoinEntity>

	@GET("convert")
	fun getConversion(
		@Query("from")
		from: String,
		@Query("to")
		to: String,
		@Query("amount")
		amount: Double,
		@Query("places")
		places: Int
	): Single<ConversionEntity>
}
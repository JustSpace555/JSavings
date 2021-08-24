package ru.jsavings.data.network.api.exchangerate

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.jsavings.data.entity.network.ConversionEntity
import ru.jsavings.data.entity.network.CryptoCoinEntity
import ru.jsavings.data.entity.network.CurrencyEntity
import ru.jsavings.data.network.common.BaseApi

/**
 * Interface of requests to ExchangeRateApi
 *
 * @author JustSpace
 */
internal interface ExchangeRateApi : BaseApi {

	/**
	 * Receives all available currencies from api
	 * @return [Single] source of [CurrencyEntity]
	 *
	 * @author JustSpace
	 */
	@GET("symbols")
	fun getCurrencies(): Single<CurrencyEntity>

	/**
	 * Receives all available crypto coins from api
	 * @return [Single] source of [CryptoCoinEntity]
	 *
	 * @author JustSpace
	 */
	@GET("cryptocurrencies")
	fun getCryptoCoins(): Single<CryptoCoinEntity>

	/**
	 * Requests conversion from one currency to another
	 * @param from Currency code or crypto currency id from which conversion is made
	 * @param to Currency code or crypto currency id to which conversion is made
	 * @param amount Amount of money to convert
	 * @param places Precision of result
	 *
	 * @author JustSpace
	 */
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
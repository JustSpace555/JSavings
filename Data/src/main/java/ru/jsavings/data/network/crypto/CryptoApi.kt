package ru.jsavings.data.network.crypto

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.jsavings.data.entity.network.crypto.CryptoCoinEntity
import ru.jsavings.data.network.common.BaseApi

internal interface CryptoApi : BaseApi {

	@GET("coins/list")
	fun getCoinsList(): Single<List<CryptoCoinEntity>>

	@GET("simple/price")
	fun getCryptoPrice(
		@Query("ids") coinIds: String,
		@Query("vs_currencies ") currencies: String
	): Single<Map<String, Map<String, Int?>>>
}
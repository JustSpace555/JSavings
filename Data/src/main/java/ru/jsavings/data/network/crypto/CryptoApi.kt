package ru.jsavings.data.network.crypto

import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import ru.jsavings.data.entity.network.crypto.CryptoCoinEntity
import ru.jsavings.data.network.common.BaseApi

internal interface CryptoApi : BaseApi {

	@GET("api/v3/coins/list")
	fun getCoinsList(
//		@Query("include_platform") includePlatform: Boolean = false
	): Observable<List<CryptoCoinEntity>>
}
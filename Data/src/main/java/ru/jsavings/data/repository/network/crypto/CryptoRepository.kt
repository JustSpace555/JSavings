package ru.jsavings.data.repository.network.crypto

import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.model.network.crypto.CryptoCoin
import ru.jsavings.data.repository.network.common.BaseNetworkRepository

interface CryptoRepository : BaseNetworkRepository {

	fun getAvailableCoinsList(): Single<List<CryptoCoin>>

	fun getCoinPrice(id: String, convertCurrency: String): Single<Map<String, Map<String, Int?>>>
}
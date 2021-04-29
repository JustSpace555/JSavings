package ru.jsavings.data.repository.network.crypto

import io.reactivex.rxjava3.core.Observable
import ru.jsavings.data.model.network.crypto.CryptoCoin
import ru.jsavings.data.repository.network.common.BaseNetworkRepository

interface CryptoRepository : BaseNetworkRepository {

	fun getAvailableCoinsList(): Observable<List<CryptoCoin>>
}
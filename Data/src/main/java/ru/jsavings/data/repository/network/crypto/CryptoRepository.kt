package ru.jsavings.data.repository.network.crypto

import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.model.network.crypto.CryptoCoin
import ru.jsavings.data.repository.common.BaseRepository

interface CryptoRepository : BaseRepository {
	fun getAvailableCryptoCoinsList(): Single<List<CryptoCoin>>
}
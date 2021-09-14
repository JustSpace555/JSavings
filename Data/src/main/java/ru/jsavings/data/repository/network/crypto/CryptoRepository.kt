package ru.jsavings.data.repository.network.crypto

import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.entity.network.CryptoCoinEntity
import ru.jsavings.data.repository.network.common.BaseNetworkRepository

/**
 * Repository of crypto coins for ExchangeRate api
 *
 * @author JustSpace
 */
interface CryptoRepository : BaseNetworkRepository {

	/**
	 * Receives all available crypto coins from api
	 * @return [Single] source of [CryptoCoinEntity]
	 *
	 * @author JustSpace
	 */
	fun getAvailableCryptoCoins(): Single<CryptoCoinEntity>
}
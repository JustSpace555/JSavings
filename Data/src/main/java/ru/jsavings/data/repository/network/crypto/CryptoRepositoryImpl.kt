package ru.jsavings.data.repository.network.crypto

import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.entity.network.CryptoCoinEntity
import ru.jsavings.data.network.api.exchangerate.ExchangeRateApi

/**
 * Implementation of [CryptoRepository]
 * @param api [ExchangeRateApi] to get data from
 *
 * @author JustSpace
 */
internal class CryptoRepositoryImpl(override val api: ExchangeRateApi) : CryptoRepository {

	override fun getAvailableCryptoCoins(): Single<CryptoCoinEntity> = api.getCryptoCoins()
}
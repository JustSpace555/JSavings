package ru.jsavings.data.repository.network.crypto

import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.mappers.network.CryptoCoinsRequestMapper
import ru.jsavings.data.model.network.crypto.CryptoCoin
import ru.jsavings.data.network.api.exchangerate.ExchangeRateApi

internal class CryptoRepositoryImpl(
	private val api: ExchangeRateApi,
	private val cryptoCoinsRequestMapper: CryptoCoinsRequestMapper
) : CryptoRepository {

	override fun getAvailableCryptoCoinsList(): Single<List<CryptoCoin>> =
		api.getCryptoCoinsList().map { cryptoCoinsRequestMapper.mapEntityToModel(it) }
}
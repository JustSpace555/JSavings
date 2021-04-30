package ru.jsavings.data.repository.network.crypto

import io.reactivex.rxjava3.core.Observable
import ru.jsavings.data.mappers.network.CryptoCoinMapper
import ru.jsavings.data.model.network.crypto.CryptoCoin
import ru.jsavings.data.network.crypto.CryptoApi

internal class CryptoRepositoryImpl(
	override val api: CryptoApi,
	private val coinMapper: CryptoCoinMapper
) : CryptoRepository {

	override fun getAvailableCoinsList(): Observable<List<CryptoCoin>> =
		api.getCoinsList().map { coinMapper.mapEntityListToModelList(it) }
}
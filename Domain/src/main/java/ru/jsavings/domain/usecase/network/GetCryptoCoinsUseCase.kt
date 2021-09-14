package ru.jsavings.domain.usecase.network

import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.repository.network.crypto.CryptoRepository
import ru.jsavings.domain.mappers.network.CryptoCoinsMapper
import ru.jsavings.domain.model.network.crypto.CryptoCoin
import ru.jsavings.domain.usecase.common.BaseUseCase

/**
 * Get all crypto coins usecase from api
 * @param repository [CryptoRepository] to interact with api
 * @param cryptoCoinsMapper [CryptoCoinsMapper] to map api call answer to list of [CryptoCoin]
 *
 * @author JustSpace
 */
class GetCryptoCoinsUseCase(
	private val repository: CryptoRepository,
	private val cryptoCoinsMapper: CryptoCoinsMapper
) : BaseUseCase {

	/**
	 * Execute usecase
	 * @return [Single] source with lists of all [CryptoCoin] in api
	 *
	 * @author JustSpace
	 */
	operator fun invoke(): Single<List<CryptoCoin>> = repository.getAvailableCryptoCoins()
		.map { cryptoCoinsMapper.mapEntityToModel(it).sortedBy { coin -> coin.id } }
}
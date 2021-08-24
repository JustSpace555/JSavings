package ru.jsavings.domain.mappers.network

import ru.jsavings.data.entity.network.CryptoCoinEntity
import ru.jsavings.domain.mappers.common.NetworkMapper
import ru.jsavings.domain.model.network.crypto.CryptoCoin

/**
 * Mapper for [CryptoCoinEntity] and list of [CryptoCoin]
 *
 * @author JustSpace
 */
class CryptoCoinsMapper : NetworkMapper<CryptoCoinEntity, List<CryptoCoin>> {

	override fun mapEntityToModel(entity: CryptoCoinEntity): List<CryptoCoin> =
		entity.cryptocurrencies.map { mapEntry ->
			CryptoCoin(
				id = mapEntry.key,
				name = mapEntry.value.name,
				symbol = mapEntry.value.symbol
			)
		}
}
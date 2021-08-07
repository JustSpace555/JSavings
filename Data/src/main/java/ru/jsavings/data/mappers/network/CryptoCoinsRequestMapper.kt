package ru.jsavings.data.mappers.network

import ru.jsavings.data.entity.network.crypto.CryptoCoinEntity
import ru.jsavings.data.mappers.common.BaseNetworkMapper
import ru.jsavings.data.model.network.crypto.CryptoCoin

internal class CryptoCoinsRequestMapper : BaseNetworkMapper<CryptoCoinEntity, List<CryptoCoin>> {

	override fun mapEntityToModel(input: CryptoCoinEntity): List<CryptoCoin> {
		val cryptoCoinList = mutableListOf<CryptoCoin>()
		input.cryptocurrencies.entries.forEach { entry ->
			cryptoCoinList.add(CryptoCoin(entry.key, entry.value.symbol, entry.value.name))
		}
		return cryptoCoinList.sortedBy { it.id }
	}
}
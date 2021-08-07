package ru.jsavings.data.entity.network.crypto

import ru.jsavings.data.entity.network.common.BaseNetworkEntity

internal data class CryptoCoinEntity(
	val success: Boolean,
	val cryptocurrencies: Map<String, CryptoCoinInfoEntity>
) : BaseNetworkEntity

internal data class CryptoCoinInfoEntity(
	val symbol: String,
	val name: String
)
package ru.jsavings.data.entity.network.crypto

import ru.jsavings.data.entity.network.common.BaseNetworkEntity

internal data class CryptoCoinEntity(
	val id: String,
	val symbol: String,
	val name: String
) : BaseNetworkEntity
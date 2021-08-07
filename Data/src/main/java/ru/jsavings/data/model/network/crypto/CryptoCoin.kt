package ru.jsavings.data.model.network.crypto

import ru.jsavings.data.model.network.common.BaseNetworkModel

data class CryptoCoin(
	val id: String,
	val symbol: String,
	val name: String
) : BaseNetworkModel() {
	override fun toString(): String = "$symbol - $name"
}
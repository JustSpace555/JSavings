package ru.jsavings.data.model.network.currency

import ru.jsavings.data.model.network.common.BaseNetworkModel

data class Currency(
	val code: String,
	val description: String,
	val symbol: String = ""
) : BaseNetworkModel() {
	override fun toString(): String = "$code - $description ($symbol)"
}
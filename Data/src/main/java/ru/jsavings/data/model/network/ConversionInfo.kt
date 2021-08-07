package ru.jsavings.data.model.network

import ru.jsavings.data.model.network.common.BaseNetworkModel

data class ConversionInfo(
	val from: String,
	val to: String,
	val amount: Double,
	val rate: Double,
	val result: Double
) : BaseNetworkModel()
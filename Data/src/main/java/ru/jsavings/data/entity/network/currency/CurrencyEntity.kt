package ru.jsavings.data.entity.network.currency

import ru.jsavings.data.entity.network.common.BaseNetworkEntity

internal data class CurrencyEntity(
	val success: Boolean,
	val symbols: Map<String, CurrencyInfoEntity>
) : BaseNetworkEntity

internal data class CurrencyInfoEntity(
	val description: String,
	val code: String
)
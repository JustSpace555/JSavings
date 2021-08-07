package ru.jsavings.data.entity.network

import ru.jsavings.data.entity.common.BaseEntity

internal data class ConversionEntity(
	val success: Boolean,
	val query: QueryInfo,
	val info: RateInfo,
	val result: Double
) : BaseEntity

internal data class QueryInfo(
	val from: String,
	val to: String,
	val amount: Double
)

internal data class RateInfo(
	val rate: Double
)
package ru.jsavings.domain.model.network

import ru.jsavings.domain.model.common.BaseModel

/**
 * Model representation of [ru.jsavings.data.entity.network.ConversionEntity]
 * @param from Currency id or code to convert from
 * @param to Currency id or code to convert to
 * @param amount Amount of money to convert
 * @param rate Rate of conversion
 * @param result Amount of money in currency with id or code = [to]
 *
 * @author JustSpace
 */
data class ConversionInfo(
	val from: String,
	val to: String,
	val amount: Double,
	val rate: Double = 0.0,
	val result: Double
) : BaseModel
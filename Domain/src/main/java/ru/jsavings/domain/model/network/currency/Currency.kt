package ru.jsavings.domain.model.network.currency

import ru.jsavings.domain.model.common.BaseModel

/**
 * Model representation of database [ru.jsavings.data.entity.network.CurrencyEntity]
 * @param code Code of currency according to api (example - USD)
 * @param name Name of currency (example - USA Dollar)
 * @param symbol Symbol of currency (example - $)
 *
 * @author JustSpace
 */
data class Currency(
	val code: String,
	val name: String,
	val symbol: String
) : BaseModel {
	override fun toString(): String = "$code $name - $symbol"
}
package ru.jsavings.domain.model.network.common

import ru.jsavings.domain.model.common.BaseModel

/**
 * Class for all currencies
 *
 * @author JustSpace
 */
abstract class BaseCurrency : BaseModel {
	abstract override fun toString(): String
}
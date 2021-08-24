package ru.jsavings.domain.model.network.crypto

import ru.jsavings.domain.model.common.BaseModel

/**
 * Model representation of database [ru.jsavings.data.entity.network.CryptoCoinEntity]
 * @param id Id of crypto coin according to api
 * @param symbol Symbol of crypto coin
 * @param name Name of crypto coin
 *
 * @author JustSpace
 */
data class CryptoCoin(
	val id: String,
	val symbol: String,
	val name: String
) : BaseModel {
	override fun toString(): String = "$id $name - $symbol"
}
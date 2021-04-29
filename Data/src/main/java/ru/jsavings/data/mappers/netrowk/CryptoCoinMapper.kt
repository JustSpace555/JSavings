package ru.jsavings.data.mappers.netrowk

import ru.jsavings.data.entity.common.BaseEntity
import ru.jsavings.data.entity.network.crypto.CryptoCoinEntity
import ru.jsavings.data.mappers.common.BaseMapper
import ru.jsavings.data.model.network.crypto.CryptoCoin

internal class CryptoCoinMapper : BaseMapper<CryptoCoinEntity, CryptoCoin> {

	override fun mapEntityToModel(
		input: CryptoCoinEntity,
		vararg additionalElements: BaseEntity
	): CryptoCoin = CryptoCoin(
		id = input.id,
		name = input.name,
		symbol = input.symbol
	)

	override fun mapModelToEntity(input: CryptoCoin): CryptoCoinEntity =
		CryptoCoinEntity(
			id = input.id,
			name = input.name,
			symbol = input.symbol
		)
}
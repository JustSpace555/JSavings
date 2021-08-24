package ru.jsavings.domain.mappers.network

import ru.jsavings.data.entity.network.ConversionEntity
import ru.jsavings.domain.mappers.common.NetworkMapper
import ru.jsavings.domain.model.network.ConversionInfo

/**
 * Mapper for [ConversionEntity] and [ConversionInfo]
 *
 * @author JustSpace
 */
class ConversionMapper : NetworkMapper<ConversionEntity, ConversionInfo> {

	override fun mapEntityToModel(entity: ConversionEntity): ConversionInfo = ConversionInfo(
		from = entity.query.from,
		to = entity.query.to,
		amount = entity.query.amount,
		rate = entity.info.rate,
		result = entity.result
	)
}
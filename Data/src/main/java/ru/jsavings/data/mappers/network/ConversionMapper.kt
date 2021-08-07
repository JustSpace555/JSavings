package ru.jsavings.data.mappers.network

import ru.jsavings.data.entity.network.ConversionEntity
import ru.jsavings.data.mappers.common.BaseNetworkMapper
import ru.jsavings.data.model.network.ConversionInfo

internal class ConversionMapper : BaseNetworkMapper<ConversionEntity, ConversionInfo> {

	override fun mapEntityToModel(input: ConversionEntity): ConversionInfo = ConversionInfo(
		from = input.query.from,
		to = input.query.to,
		amount = input.query.amount,
		rate = input.info.rate,
		result = input.result
	)
}
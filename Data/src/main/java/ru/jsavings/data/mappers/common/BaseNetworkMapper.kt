package ru.jsavings.data.mappers.common

import ru.jsavings.data.entity.common.BaseEntity

interface BaseNetworkMapper<E : BaseEntity, M> : BaseMapper<E, M> {
	fun mapEntityToModel(input: E): M
}
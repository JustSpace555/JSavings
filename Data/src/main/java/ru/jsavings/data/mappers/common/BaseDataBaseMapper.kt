package ru.jsavings.data.mappers.common

import ru.jsavings.data.entity.common.BaseEntity
import ru.jsavings.data.model.common.BaseModel

interface BaseDataBaseMapper <E : BaseEntity, M : BaseModel> : BaseMapper<E, M> {

	fun mapEntityToModel(input: E, vararg additionalElements: BaseEntity): M
	fun mapModelToEntity(input: M): E

	fun mapEntityListToModelList(input: List<E>, vararg additionalElements: BaseEntity) =
		input.map { mapEntityToModel(it, *additionalElements) }

	fun mapModelListToEntityList(input: List<M>) =
		input.map { mapModelToEntity(it) }
}
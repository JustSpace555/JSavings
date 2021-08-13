package ru.jsavings.domain.usecase.mappers

import ru.jsavings.data.entity.common.BaseEntity
import ru.jsavings.domain.usecase.model.BaseModel

interface BaseMapper <E : BaseEntity, M : BaseModel> {

	fun mapEntityToModel(input: E, vararg additionalElements: BaseEntity): M
	fun mapModelToEntity(input: M): E

	fun mapEntityListToModelList(input: List<E>, vararg additionalElements: BaseEntity) =
		input.map { mapEntityToModel(it, *additionalElements) }

	fun mapModelListToEntityList(input: List<M>) =
		input.map { mapModelToEntity(it) }
}
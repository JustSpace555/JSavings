package ru.jsavings.data.mappers

import ru.jsavings.data.entity.BaseEntity
import ru.jsavings.data.model.BaseModel

interface BaseMapper <E : BaseEntity, M : BaseModel> {

	fun mapEntityToModel(input: E, vararg additionalElements: BaseModel): M
	fun mapModelToEntity(input: M, vararg additionalElementIds: Int): E

	fun mapEntityListToModelList(input: List<E>, vararg additionalElements: BaseModel) =
		input.map { mapEntityToModel(it, *additionalElements) }

	fun mapModelListToEntityList(input: List<M>, vararg additionalElementIds: Int) =
		input.map { mapModelToEntity(it, *additionalElementIds) }
}
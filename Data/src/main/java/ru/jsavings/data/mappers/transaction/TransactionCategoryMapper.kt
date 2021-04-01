package ru.jsavings.data.mappers.transaction

import ru.jsavings.data.entity.transaction.TransactionCategoryEntity
import ru.jsavings.data.mappers.BaseMapper
import ru.jsavings.data.model.BaseModel
import ru.jsavings.data.model.transaction.category.TransactionCategory
import ru.jsavings.data.model.transaction.category.TransactionCategoryType

class TransactionCategoryMapper : BaseMapper<TransactionCategoryEntity, TransactionCategory> {

	override fun mapEntityToModel(
		input: TransactionCategoryEntity,
		vararg additionalElements: BaseModel
	): TransactionCategory =
		TransactionCategory(
			name = input.name,
			type = TransactionCategoryType.valueOf(input.type),
			color = input.color,
			iconPath = input.iconPath
		)

	override fun mapModelToEntity(
		input: TransactionCategory,
		vararg additionalElementIds: Int
	): TransactionCategoryEntity =
		TransactionCategoryEntity(
			categoryId = 0,
			name = input.name,
			type = input.type.toString(),
			color = input.color,
			iconPath = input.iconPath
		)
}
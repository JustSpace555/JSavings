package ru.jsavings.data.mappers.transaction

import ru.jsavings.data.entity.BaseEntity
import ru.jsavings.data.entity.transaction.TransactionCategoryEntity
import ru.jsavings.data.mappers.BaseMapper
import ru.jsavings.data.model.BaseModel
import ru.jsavings.data.model.transaction.TransactionCategory
import ru.jsavings.data.model.transaction.TransactionCategoryType

internal class TransactionCategoryMapper : BaseMapper<TransactionCategoryEntity, TransactionCategory> {

	override fun mapEntityToModel(
		input: TransactionCategoryEntity,
		vararg additionalElements: BaseEntity
	): TransactionCategory =
		TransactionCategory(
			name = input.categoryName,
			accountName = input.accountFkName,
			categoryType = TransactionCategoryType.valueOf(input.type),
			color = input.color,
			iconPath = input.iconPath
		)

	override fun mapModelToEntity(input: TransactionCategory): TransactionCategoryEntity =
		TransactionCategoryEntity(
			categoryName = input.name,
			accountFkName = input.accountName,
			type = input.categoryType.toString(),
			color = input.color,
			iconPath = input.iconPath
		)
}
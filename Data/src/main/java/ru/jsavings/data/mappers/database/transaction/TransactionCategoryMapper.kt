package ru.jsavings.data.mappers.database.transaction

import ru.jsavings.data.entity.common.BaseEntity
import ru.jsavings.data.entity.database.transaction.TransactionCategoryEntity
import ru.jsavings.data.mappers.common.BaseMapper
import ru.jsavings.data.model.database.transaction.TransactionCategory
import ru.jsavings.data.model.database.transaction.TransactionCategoryType

internal class TransactionCategoryMapper :
	BaseMapper<TransactionCategoryEntity, TransactionCategory> {

	override fun mapEntityToModel(
		input: TransactionCategoryEntity,
		vararg additionalElements: BaseEntity
	): TransactionCategory =
		TransactionCategory(
			categoryId = input.categoryId,
			name = input.categoryName,
			accountId = input.accountFkId,
			categoryType = TransactionCategoryType.valueOf(input.type),
			color = input.color,
			iconPath = input.iconPath
		)

	override fun mapModelToEntity(input: TransactionCategory): TransactionCategoryEntity =
		TransactionCategoryEntity(
			categoryId = input.categoryId,
			categoryName = input.name,
			accountFkId = input.accountId,
			type = input.categoryType.toString(),
			color = input.color,
			iconPath = input.iconPath
		)
}
package ru.jsavings.domain.usecase.mappers.transaction

import ru.jsavings.data.entity.common.BaseEntity
import ru.jsavings.data.entity.TransactionCategoryEntity
import ru.jsavings.domain.usecase.mappers.BaseMapper
import ru.jsavings.domain.usecase.model.transaction.TransactionCategory
import ru.jsavings.domain.usecase.model.transaction.TransactionCategoryType

internal class TransactionCategoryMapper : BaseMapper<TransactionCategoryEntity, TransactionCategory> {

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
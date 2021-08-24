package ru.jsavings.domain.mappers.database

import ru.jsavings.data.entity.database.TransactionCategoryEntity
import ru.jsavings.domain.mappers.common.DataBaseMapper
import ru.jsavings.domain.model.database.transaction.category.TransactionCategory
import ru.jsavings.domain.model.database.transaction.category.TransactionCategoryType

/**
 * Mapper for [TransactionCategoryEntity] and [TransactionCategory]
 *
 * @author JustSpace
 */
class TransactionCategoryMapper : DataBaseMapper<TransactionCategoryEntity, TransactionCategory> {

	override fun mapEntityToModel(entity: TransactionCategoryEntity): TransactionCategory = TransactionCategory(
		categoryId = entity.categoryId,
		accountId = entity.accountFkId,
		categoryType = TransactionCategoryType.valueOf(entity.type),
		color = entity.color,
		iconPath = entity.iconPath,
		name = entity.categoryName
	)

	override fun mapModelToEntity(model: TransactionCategory): TransactionCategoryEntity = TransactionCategoryEntity(
		categoryId = model.categoryId,
		accountFkId = model.accountId,
		categoryName = model.name,
		type = model.categoryType.toString(),
		color = model.color,
		iconPath = model.iconPath
	)
}
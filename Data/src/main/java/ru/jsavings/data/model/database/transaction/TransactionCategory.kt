package ru.jsavings.data.model.database.transaction

import ru.jsavings.data.model.database.common.BaseDbModel

data class TransactionCategory (
	val categoryId: Int = 0,
	val name: String,
	val accountId: Int,
	val categoryType: TransactionCategoryType,
	val color: String,
	val iconPath: String
) : BaseDbModel
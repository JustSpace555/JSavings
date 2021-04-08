package ru.jsavings.data.model.transaction

import ru.jsavings.data.model.BaseModel

data class TransactionCategory (
	val name: String,
	val accountName: String,
	val categoryType: TransactionCategoryType,
	val color: String,
	val iconPath: String
) : BaseModel
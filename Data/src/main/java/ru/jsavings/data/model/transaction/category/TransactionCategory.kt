package ru.jsavings.data.model.transaction.category

import ru.jsavings.data.model.BaseModel

data class TransactionCategory (
	val name: String,
	val type: TransactionCategoryType,
	val color: String,
	val iconPath: String
) : BaseModel
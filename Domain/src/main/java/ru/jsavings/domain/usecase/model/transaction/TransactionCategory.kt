package ru.jsavings.domain.usecase.model.transaction

import ru.jsavings.domain.usecase.model.BaseModel

data class TransactionCategory (
	val categoryId: Int = 0,
	val name: String,
	val accountId: Int,
	val categoryType: TransactionCategoryType,
	val color: String,
	val iconPath: String
) : BaseModel
package ru.jsavings.data.model.purse

import ru.jsavings.data.model.BaseModel

data class Purse (
	val accountId: Int,
	val name: String,
	val balance: Double,
	val currency: String,
	val category: PurseCategoryType,
	val color: String,
	val iconPath: String
) : BaseModel
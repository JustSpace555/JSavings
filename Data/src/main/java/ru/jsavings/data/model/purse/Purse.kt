package ru.jsavings.data.model.purse

data class Purse (
	val accountId: Int,
	val name: String,
	val balance: Double,
	val currency: String,
	val category: PurseCategoryType,
	val color: String,
	val iconPath: String
)
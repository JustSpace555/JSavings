package ru.jsavings.domain.usecase.model.purse

import ru.jsavings.domain.usecase.model.Account
import ru.jsavings.domain.usecase.model.BaseModel

data class Purse (
	val purseId: Int = 0,
	val name: String,
	val account: Account,
	val balance: Double,
	val currency: String,
	val category: PurseCategoryType,
	val color: String,
	val iconPath: String
) : BaseModel
package ru.jsavings.data.model.database.purse

import ru.jsavings.data.model.database.Account
import ru.jsavings.data.model.common.BaseModel
import ru.jsavings.data.model.database.common.BaseDbModel

data class Purse (
	val purseId: Int = 0,
	val name: String,
	val account: Account,
	val balance: Double,
	val currency: String,
	val category: PurseCategoryType,
	val color: String,
	val iconPath: String
) : BaseDbModel
package ru.jsavings.data.model.database.purse

import ru.jsavings.data.model.database.Account
import ru.jsavings.data.model.database.common.BaseDbModel

data class Purse (
	val purseId: Int = 0,
	val name: String,
	val account: Account,
	val balance: Double,
	val currency: String,
	val category: PurseCategoryType,
	val creditLimit: Double?,
	val interestRate: Double?,
	val paymentDay: Int?,
	val gracePeriod: Int?,
	val color: String,
	val iconPath: String
) : BaseDbModel
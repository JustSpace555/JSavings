package ru.jsavings.data.model.database.purse

import ru.jsavings.data.model.database.Account
import ru.jsavings.data.model.database.common.BaseDbModel

data class Purse (
	val purseId: Long = 0,
	val name: String,
	val account: Account,
	val balance: Double,
	val currency: String,
	val category: PurseCategoryType,
	val creditLimit: Double = 0.0,
	val interestRate: Double = 0.0,
	val paymentDay: Int = 0,
	val gracePeriod: Int = 0,
	val color: Int,
	val iconPath: String
) : BaseDbModel
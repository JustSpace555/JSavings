package ru.jsavings.data.model

import java.util.*

data class Account (
	val accountId: Int = 0,
	val name: String,
	val mainCurrency: Currency,
	val balanceInMainCurrency: Double
): BaseModel
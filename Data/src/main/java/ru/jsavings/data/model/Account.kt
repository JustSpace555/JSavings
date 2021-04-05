package ru.jsavings.data.model

import java.util.*

data class Account (
	val name: String,
	val mainCurrency: Currency,
	val balanceInMainCurrency: Double
): BaseModel
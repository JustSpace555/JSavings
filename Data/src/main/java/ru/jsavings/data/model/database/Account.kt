package ru.jsavings.data.model.database

import ru.jsavings.data.model.common.BaseModel

data class Account (
	val accountId: Long = 0,
	val name: String,
	val mainCurrencyCode: String,
	val balanceInMainCurrency: Double
): BaseModel
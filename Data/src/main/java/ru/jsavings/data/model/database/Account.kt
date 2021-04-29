package ru.jsavings.data.model.database

import ru.jsavings.data.model.common.BaseModel
import java.util.*

data class Account (
	val accountId: Int = 0,
	val name: String,
	val mainCurrency: Currency,
	val balanceInMainCurrency: Double
): BaseModel
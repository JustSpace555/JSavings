package ru.jsavings.data.model.transaction

import ru.jsavings.data.model.BaseModel
import java.util.*

data class Transaction (
	val transactionId: Long,
	val purseName: String,
	val transactionCategory: TransactionCategory,
	val totalSum: Double,
	val date: Date,
	val description: String = "",
	val describePicturePath: String? = null
) : BaseModel
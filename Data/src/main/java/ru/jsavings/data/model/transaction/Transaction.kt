package ru.jsavings.data.model.transaction

import ru.jsavings.data.model.BaseModel
import ru.jsavings.data.model.transaction.category.TransactionCategory
import java.util.*

data class Transaction (
	val purseId: Int,
	val transactionCategory: TransactionCategory,
	val totalSum: Double,
	val date: Date,
	val description: String = "",
	val describePicturePath: String? = null
) : BaseModel
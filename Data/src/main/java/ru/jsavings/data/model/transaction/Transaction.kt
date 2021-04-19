package ru.jsavings.data.model.transaction

import ru.jsavings.data.model.BaseModel
import ru.jsavings.data.model.purse.Purse
import java.util.*

data class Transaction (
	val transactionId: Long = 0,
	val purse: Purse,
	val transactionCategoryId: Int,
	val totalSum: Double,
	val date: Date,
	val description: String = "",
	val describePicturePath: String? = null
) : BaseModel
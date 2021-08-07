package ru.jsavings.data.model.database.transaction

import ru.jsavings.data.model.database.common.BaseDbModel
import ru.jsavings.data.model.database.purse.Purse
import java.util.*

data class Transaction (
	val transactionId: Long = 0,
	val purse: Purse,
	val transactionCategoryId: Long,
	val totalSum: Double,
	val date: Date,
	val description: String = "",
	val describePicturePath: String? = null
) : BaseDbModel
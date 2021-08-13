package ru.jsavings.domain.usecase.model.transaction

import ru.jsavings.domain.usecase.model.BaseModel
import ru.jsavings.domain.usecase.model.purse.Purse
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
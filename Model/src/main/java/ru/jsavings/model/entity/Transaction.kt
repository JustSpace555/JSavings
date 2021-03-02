package ru.jsavings.model.entity

import androidx.room.*
import ru.jsavings.model.converter.DateConverter
import java.util.*

@Entity
data class Transaction (

	//Transaction id
	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "transaction_id")
	val transactionId: Long,

	//Category id (food, entertainment, ...)
	@ColumnInfo(name = "category_id")
	val categoryId: String,

	//Total amount spent
	@ColumnInfo(name = "total_sum")
	val totalSum: Int,

	//Date of transaction
	@TypeConverters(DateConverter::class)
	val date: Date,

	//Purse id
	@ColumnInfo(name = "purse_id")
	val purseId: Int,

	//Description
	val description: String? = null,

	//Path to icon
	@ColumnInfo(name = "icon_path")
	val iconPath: String
)
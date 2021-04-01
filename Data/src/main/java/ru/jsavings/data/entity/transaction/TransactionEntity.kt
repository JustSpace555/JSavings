package ru.jsavings.data.entity.transaction

import androidx.room.*
import ru.jsavings.data.entity.BaseEntity
import ru.jsavings.data.entity.PurseEntity

@Entity (
	tableName = "transaction_table",
	foreignKeys = [
		ForeignKey(entity = PurseEntity::class, parentColumns = ["transaction_id"], childColumns = ["purse_id"])
	]
)
data class TransactionEntity (

	//Transaction id
	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "transaction_id")
	val transactionId: Long,

	//Purse id which this transaction belongs to
	@ColumnInfo(name = "purse_id")
	val purseId: Int,

	//Category id (food, entertainment, ...)
	@ColumnInfo(name = "transaction_category_id")
	val transactionCategoryId: Int,

	//Total amount spent
	@ColumnInfo(name = "total_sum")
	val totalSum: Double,

	//Date of transaction
	val date: Long,

	//Description
	val description: String,

	//Path of picture attached to this transaction
	@ColumnInfo(name = "describe_picture_path")
	val describePicturePath: String?
) : BaseEntity
package ru.jsavings.data.entity.transaction

import androidx.room.*
import ru.jsavings.data.entity.BaseEntity
import ru.jsavings.data.entity.PurseEntity

@Entity (
	tableName = "transaction_table",
	foreignKeys = [
		ForeignKey(entity = PurseEntity::class, childColumns = ["purse_fk_id"], parentColumns = ["purse_id"]),
		ForeignKey(
			entity = TransactionCategoryEntity::class,
			childColumns = ["category_fk_id"],
			parentColumns = ["category_id"]
		)
	]
)
internal data class TransactionEntity (

	//Transaction id
	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "transaction_id")
	val transactionId: Long,

	//Purse id which this transaction belongs to
	@ColumnInfo(name = "purse_fk_id")
	val purseFkId: Int,

	@ColumnInfo(name = "category_fk_id")
	val categoryFkId: Int,

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
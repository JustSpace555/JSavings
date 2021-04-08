package ru.jsavings.data.entity.transaction

import androidx.room.*
import ru.jsavings.data.entity.BaseEntity
import ru.jsavings.data.entity.PurseEntity

@Entity (
	tableName = "transaction_table",
	foreignKeys = [
		ForeignKey(entity = PurseEntity::class, childColumns = ["purse_fk_name"], parentColumns = ["purse_name"]),
		ForeignKey(
			entity = TransactionCategoryEntity::class,
			childColumns = ["category_fk_name"],
			parentColumns = ["category_name"]
		)
	]
)
internal data class TransactionEntity (

	//Transaction id
	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "transaction_id")
	val transactionId: Long,

	//Purse id which this transaction belongs to
	@ColumnInfo(name = "purse_fk_name", index = true)
	val purseFkName: String,

	//Category id (food, entertainment, ...)
	@ColumnInfo(name = "category_fk_name", index = true)
	val categoryFkName: String,

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
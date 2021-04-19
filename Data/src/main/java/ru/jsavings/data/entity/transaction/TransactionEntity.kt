package ru.jsavings.data.entity.transaction

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import ru.jsavings.data.entity.BaseEntity
import ru.jsavings.data.entity.PurseEntity

@Entity (
	tableName = "transaction_table",
	foreignKeys = [
		ForeignKey(
			entity = PurseEntity::class,
			childColumns = ["purse_fk_id"],
			parentColumns = ["purse_id"],
			onDelete = ForeignKey.CASCADE
		),
		ForeignKey(
			entity = TransactionCategoryEntity::class,
			childColumns = ["category_fk_id"],
			parentColumns = ["category_id"],
			onDelete = ForeignKey.CASCADE
		)
	]
)
internal data class TransactionEntity (

	//Transaction id
	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "transaction_id")
	val transactionId: Long,

	//Purse id which this transaction belongs to
	@ColumnInfo(name = "purse_fk_id", index = true)
	val purseFkId: Int,

	//Category id (food, entertainment, ...)
	@ColumnInfo(name = "category_fk_id", index = true)
	val categoryFkId: Int,

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
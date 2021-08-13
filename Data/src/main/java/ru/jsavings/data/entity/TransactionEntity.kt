package ru.jsavings.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import ru.jsavings.data.entity.common.BaseEntity

/**
 * Entity of transaction in database
 * @param transactionId Id of transaction in database
 * @param walletFkId Id of [WalletEntity] to which this transaction belongs
 * @param categoryFkId Id of [TransactionCategoryEntity] to which this transaction belongs
 * @param sum Amount of money of this transaction
 * @param date [java.util.Date]'s Long representation of this transaction
 * @param description Description of this transaction
 * @param describePicturePath Path to image of this transaction
 *
 * @author JustSpace
 */
@Entity (
	tableName = "transaction_table",
	foreignKeys = [
		ForeignKey(
			entity = WalletEntity::class,
			childColumns = ["wallet_fk_id"],
			parentColumns = ["wallet_id"],
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

	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "transaction_id")
	val transactionId: Long,

	@ColumnInfo(name = "wallet_fk_id", index = true)
	val walletFkId: Int,

	@ColumnInfo(name = "category_fk_id", index = true)
	val categoryFkId: Int,

	@ColumnInfo(name = "total_sum")
	val sum: Double,

	val date: Long,
	val description: String,

	@ColumnInfo(name = "describe_picture_path")
	val describePicturePath: String?

) : BaseEntity
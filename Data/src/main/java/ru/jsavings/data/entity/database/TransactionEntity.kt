package ru.jsavings.data.entity.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import ru.jsavings.data.entity.database.common.BaseDbEntity

/**
 * Entity of transaction in database
 * @param transactionId Id of transaction in database
 * @param fromWalletFkId Id of [WalletEntity] from which transaction goes.
 * Must be not null if [TransactionCategoryEntity.type] is CONSUMPTION or TRANSFER
 * @param toWalletFkId If of [WalletEntity] to which transfer transaction goes.
 * Must be not null if [TransactionCategoryEntity.type] is INCOME or TRANSFER
 * @param categoryFkId Id of [TransactionCategoryEntity] to which this transaction belongs. Must be null only if
 * transaction type is TRANSFER
 * @param sumInWalletCurrency Amount of money of this transaction
 * @param transferSum Amount of money in [toWalletFkId] wallet currency, which were transferred into this wallet.
 * Must be not null only if [TransactionCategoryEntity.type] is TRANSFER
 * @param sumInAccountCurrency Sum of transaction in account's currency to which it belongs
 * @param dateDay [java.util.Date]'s Long representation of day of this transaction
 * @param dateTime [java.util.Date]'s Long representation of time of this transaction
 * @param description Description of this transaction
 * @param describePicturePath Path to image of this transaction
 *
 * @author JustSpace
 */
@Entity(
	tableName = "transaction_table",
	foreignKeys = [
		ForeignKey(
			entity = WalletEntity::class,
			childColumns = ["from_wallet_fk_id"],
			parentColumns = ["wallet_id"],
			onDelete = ForeignKey.CASCADE
		),
		ForeignKey(
			entity = WalletEntity::class,
			childColumns = ["to_wallet_fk_id"],
			parentColumns = ["wallet_id"],
			onDelete = ForeignKey.CASCADE
		),
		ForeignKey(
			entity = TransactionCategoryEntity::class,
			childColumns = ["category_fk_id"],
			parentColumns = ["category_id"],
			onDelete = ForeignKey.CASCADE
		),
		ForeignKey(
			entity = AccountEntity::class,
			childColumns = ["account_fk_id"],
			parentColumns = ["account_id"],
			onDelete = ForeignKey.CASCADE
		)
	]
)
data class TransactionEntity (

	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "transaction_id")
	val transactionId: Long = 0,

	@ColumnInfo(name = "from_wallet_fk_id", index = true)
	val fromWalletFkId: Long?,

	@ColumnInfo(name = "to_wallet_fk_id", index = true)
	val toWalletFkId: Long?,

	@ColumnInfo(name = "category_fk_id", index = true)
	val categoryFkId: Long?,

	@ColumnInfo(name = "account_fk_id", index = true)
	val accountFkId: Long,

	@ColumnInfo(name = "wallet_currency_su,")
	val sumInWalletCurrency: Double,

	@ColumnInfo(name = "transfer_sum")
	val transferSum: Double?,

	@ColumnInfo(name = "main_currency_sum")
	val sumInAccountCurrency: Double,

	@ColumnInfo(name = "date_day")
	val dateDay: Long,

	@ColumnInfo(name = "date_time")
	val dateTime: Long,

	val description: String,

	@ColumnInfo(name = "describe_picture_path")
	val describePicturePath: String

) : BaseDbEntity
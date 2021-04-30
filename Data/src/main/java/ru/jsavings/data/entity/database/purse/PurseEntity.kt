package ru.jsavings.data.entity.database.purse

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import ru.jsavings.data.entity.database.account.AccountEntity
import ru.jsavings.data.entity.database.common.BaseDbEntity

@Entity (
	tableName = "purse_table",
	foreignKeys = [
		ForeignKey(
			entity = AccountEntity::class,
			childColumns = ["account_fk_id"],
			parentColumns = ["account_id"],
			onDelete = ForeignKey.CASCADE
		)
	]
)
internal data class PurseEntity (

	//Id of purse
	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "purse_id")
	val purseId: Int = 0,

	//Name of purse
	@ColumnInfo(name = "purse_name")
	val purseName: String,

	@ColumnInfo(name = "account_fk_id", index = true)
	val accountFkId: Int,

	//Balance on purse
	val balance: Double,

	//Current purse's currency ($, BTC, Kilogram, amount (for securities))
	val currency: String,

	// Purse category (PurseCategoryType)
	val category: String,

	//Credit purse limit
	@ColumnInfo(name = "credit_limit")
	val creditLimit: Double?,

	//Credit purse interest rate
	@ColumnInfo(name = "interest_rate")
	val interestRate: Double?,

	//Credit purse day of payment
	@ColumnInfo(name = "payment_day")
	val paymentDay: Int?,

	//Credit purse grace period
	@ColumnInfo(name = "grace_period")
	val gracePeriod: Int?,

	//Purse's color (#0xFFFFFFFF)
	val color: String,

	//Path to icon
	@ColumnInfo(name = "icon_path")
	val iconPath: String

) : BaseDbEntity
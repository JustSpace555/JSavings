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

	//Purse's color (#0xFFFFFFFF)
	val color: String,

	//Path to icon
	@ColumnInfo(name = "icon_path")
	val iconPath: String

) : BaseDbEntity
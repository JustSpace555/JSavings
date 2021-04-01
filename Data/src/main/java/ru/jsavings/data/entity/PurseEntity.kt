package ru.jsavings.data.entity

import androidx.room.*
import ru.jsavings.data.entity.AccountEntity

@Entity (
	tableName = "purse_table",
	foreignKeys = [
		ForeignKey(entity = AccountEntity::class, parentColumns = ["purse_id"], childColumns = ["account_id"])
	]
)
data class PurseEntity (

	//Purse id
	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "purse_id")
	val purseId: Int,

	@ColumnInfo(name = "account_id")
	val accountId: Int,

	//Name of purse
	val name: String,

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
) : BaseEntity
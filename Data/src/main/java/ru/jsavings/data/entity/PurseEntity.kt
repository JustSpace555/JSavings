package ru.jsavings.data.entity

import androidx.room.*
import ru.jsavings.data.entity.AccountEntity

@Entity (
	tableName = "purse_table",
	foreignKeys = [
		ForeignKey(entity = AccountEntity::class, childColumns = ["account_fk_name"], parentColumns = ["account_name"])
	]
)
internal data class PurseEntity (

	//Name of purse
	@PrimaryKey
	@ColumnInfo(name = "purse_name")
	val purseName: String,

	@ColumnInfo(name = "account_fk_name", index = true)
	val accountFkName: String,

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
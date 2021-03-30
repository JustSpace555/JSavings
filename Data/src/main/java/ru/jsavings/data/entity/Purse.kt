package ru.jsavings.data.entity

import androidx.room.*
import ru.jsavings.data.database.converters.CurrencyConverter
import ru.jsavings.data.database.converters.IdListConverter
import java.util.*

@Entity
data class Purse (
	//Purse id
	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "purse_id")
	val purseId: Int,

	//Name of purse
	val name: String,

	//Balance on purse
	val balance: Int,

	//Current purse's currency
	@TypeConverters(CurrencyConverter::class)
	val currency: Currency,

	//All transactions in purse
	@ColumnInfo(name = "transactions")
	@TypeConverters(IdListConverter::class)
	val transactionsIdList: List<Int>,

	//Purse's color (#0xFFFFFFFF)
	val color: String,

	//Path to icon
	@ColumnInfo(name = "icon_path")
	val iconPath: String
)
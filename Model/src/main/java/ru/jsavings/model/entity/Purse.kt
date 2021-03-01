package ru.jsavings.model.entity

import androidx.room.*
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
	@TypeConverters(TransactionsIdListConverter::class)
	val transactionsIdList: List<Int>,

	//Purse's color (#0xFFFFFFFF)
	val color: String,

	//Path to icon
	@ColumnInfo(name = "icon_path")
	val iconPath: String
) {
	companion object {
		class CurrencyConverter {

			@TypeConverter
			fun currencyToString(inputCurrency: Currency): String = inputCurrency.currencyCode

			@TypeConverter
			fun stringToCurrency(inputCurrencyString: String): Currency = Currency.getInstance(inputCurrencyString)
		}

		class TransactionsIdListConverter {

			companion object {
				private const val TRANSACTIONS_ID_SEPARATOR = ","
			}

			@TypeConverter
			fun transactionsIdListToString(inputList: List<Int>): String =
				inputList.joinToString(TRANSACTIONS_ID_SEPARATOR)

			@TypeConverter
			fun stringToTransactionsIdList(inputString: String): List<Int> =
				inputString.split(TRANSACTIONS_ID_SEPARATOR).map { it.toInt() }
		}
	}
}
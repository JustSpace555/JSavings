package ru.jsavings.model.entity

import androidx.room.*
import java.text.SimpleDateFormat
import java.util.*

@Entity
data class Transaction (

	//Transaction id
	//TODO Подумать сделать ли Long вместо Int
	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "transaction_id")
	val transactionId: Int,

	//Category id (food, entertainment, ...)
	@ColumnInfo(name = "category_id")
	val categoryId: String,

	//Total amount spent
	@ColumnInfo(name = "total_sum")
	val totalSum: Int,

	//Date of transaction
	@TypeConverters(DateConverter::class)
	val date: Date,

	//Purse id
	@ColumnInfo(name = "purse_id")
	val purseId: Int,

	//Description
	val description: String? = null,

	//Path to icon
	@ColumnInfo(name = "icon_path")
	val iconPath: String
) {
	companion object {

		//TODO установить правильную локаль
		class DateConverter {

			companion object {
				private const val DATE_REPRESENTATION = "yyyy-MM-dd HH:mm:ss"
			}

			@TypeConverter
			fun fromDateToString(inputDate: Date): String {
				val iso8601Format = SimpleDateFormat(DATE_REPRESENTATION, Locale.getDefault())

				return iso8601Format.format(inputDate)
			}

			@TypeConverter
			fun fromStringToDate(inputDateString: String): Date {
				val iso8601Format = SimpleDateFormat(DATE_REPRESENTATION, Locale.getDefault())

				return iso8601Format.parse(inputDateString)!! //TODO Постараться убрать !!
			}
		}
	}
}
package ru.jsavings.data.database.converters

import androidx.room.TypeConverter
import java.util.*

class DateConverter {

	@TypeConverter
	fun fromDateToString(inputDate: Date): Long = inputDate.time

	@TypeConverter
	fun fromStringToDate(inputDateLong: Long): Date = Date(inputDateLong)
}
package ru.jsavings.model.converter

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class DateConverter {

	@TypeConverter
	fun fromDateToString(inputDate: Date): Long = inputDate.time

	@TypeConverter
	fun fromStringToDate(inputDateLong: Long): Date = Date(inputDateLong)
}
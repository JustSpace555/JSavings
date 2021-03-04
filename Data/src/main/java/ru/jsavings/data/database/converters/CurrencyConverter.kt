package ru.jsavings.data.database.converters

import androidx.room.TypeConverter
import java.util.*

class CurrencyConverter {

	@TypeConverter
	fun currencyToString(inputCurrency: Currency): String = inputCurrency.currencyCode

	@TypeConverter
	fun stringToCurrency(inputCurrencyString: String): Currency = Currency.getInstance(inputCurrencyString)
}
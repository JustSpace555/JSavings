package ru.jsavings.model.converter

import androidx.room.TypeConverter
import java.util.*

class CurrencyConverter {

	@TypeConverter
	fun currencyToString(inputCurrency: Currency): String = inputCurrency.currencyCode

	@TypeConverter
	fun stringToCurrency(inputCurrencyString: String): Currency = Currency.getInstance(inputCurrencyString)
}
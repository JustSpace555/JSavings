package ru.jsavings.presentation.extension

import java.util.*

/**
 * Get symbol of currency
 * @return Symbol of currency according to this string (currency code) or this string if there is no such currency
 *
 * @author JustSpace
 */
fun String.getCurrencySymbol(): String = try {
	Currency.getInstance(this).symbol
} catch (e: IllegalArgumentException) {
	this
}

/**
 * Checks if string is not empty AND is not blank
 * @return True if input string is not empty AND not blank, false otherwise
 *
 * @author JustSpace
 */
fun String.isNotEmptyAndNotBlank(): Boolean = isNotEmpty() && isNotBlank()
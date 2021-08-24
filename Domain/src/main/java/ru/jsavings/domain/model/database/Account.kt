package ru.jsavings.domain.model.database

import ru.jsavings.domain.model.common.BaseModel

/**
 * Model for [ru.jsavings.data.entity.database.AccountEntity]
 * @param accountId Account id in database
 * @param name Name of account
 * @param mainCurrencyCode Code of main currency according to [java.util.Currency] (usd, eur, ...)
 * @param balanceInMainCurrency Amount if money in currency which id is [mainCurrencyCode]
 *
 * @author JustSpace
 */
data class Account (
	val accountId: Long = 0,
	val name: String,
	val mainCurrencyCode: String,
	val balanceInMainCurrency: Double
): BaseModel
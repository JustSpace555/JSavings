package ru.jsavings.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.jsavings.data.entity.common.BaseEntity

/**
 * Entity of account in database
 * @param accountId Id of account in database
 * @param accountName Name of account
 * @param mainCurrencyCode Code of main currency of account, according to api or [java.util.Currency] (usd, bitcoin, ...)
 * @param balanceInMainCurrency Balance of account in currency which id is [mainCurrencyCode]
 *
 * @author JustSpace
 */
@Entity (tableName = "account_table")
internal data class AccountEntity (

	@PrimaryKey
	@ColumnInfo(name = "account_id")
	val accountId: Long = 0,

	@ColumnInfo(name = "account_name")
	val accountName: String,

	@ColumnInfo(name = "main_currency")
	val mainCurrencyCode: String,

	@ColumnInfo(name = "balance_in_main_currency")
	val balanceInMainCurrency: Double

) : BaseEntity
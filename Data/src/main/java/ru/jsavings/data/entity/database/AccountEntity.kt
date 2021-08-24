package ru.jsavings.data.entity.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.jsavings.data.entity.common.BaseEntity
import ru.jsavings.data.entity.database.common.BaseDbEntity

/**
 * Entity of account in database
 * @param accountId Id of account in database
 * @param accountName Name of account
 * @param mainCurrencyCode Code of main currency of account, according to [java.util.Currency] (usd, eur, ...)
 * @param balanceInMainCurrency Balance of account in currency which id is [mainCurrencyCode]
 *
 * @author JustSpace
 */
@Entity (tableName = "account_table")
data class AccountEntity (

	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "account_id")
	val accountId: Long = 0,

	@ColumnInfo(name = "account_name")
	val accountName: String,

	@ColumnInfo(name = "main_currency")
	val mainCurrencyCode: String,

	@ColumnInfo(name = "balance_in_main_currency")
	val balanceInMainCurrency: Double

) : BaseDbEntity
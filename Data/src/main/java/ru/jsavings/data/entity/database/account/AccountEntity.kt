package ru.jsavings.data.entity.database.account

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.jsavings.data.entity.database.common.BaseDbEntity

@Entity (tableName = "account_table")
internal data class AccountEntity (

	@PrimaryKey
	@ColumnInfo(name = "account_id")
	val accountId: Int = 0,

	//Account name
	@ColumnInfo(name = "account_name")
	val accountName: String,

	//Main currency of this account
	@ColumnInfo(name = "main_currency_code")
	val mainCurrency: String,

	@ColumnInfo(name = "balance_in_main_currency")
	val balanceInMainCurrency: Double

) : BaseDbEntity
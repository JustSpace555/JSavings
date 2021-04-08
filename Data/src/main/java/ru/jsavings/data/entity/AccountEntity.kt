package ru.jsavings.data.entity

import androidx.room.*

@Entity (tableName = "account_table")
internal data class AccountEntity (

	//Account name
	@PrimaryKey
	@ColumnInfo(name = "account_name")
	val accountName: String,

	//Main currency of this account
	@ColumnInfo(name = "main_currency")
	val mainCurrency: String,

	//
	@ColumnInfo(name = "balance_in_main_currency")
	val balanceInMainCurrency: Double

) : BaseEntity
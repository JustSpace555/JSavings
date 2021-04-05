package ru.jsavings.data.entity

import androidx.room.*

@Entity (tableName = "account_table")
internal data class AccountEntity (

	//Account id
	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "account_id")
	val accountId: Int,

	//Account name
	val name: String,

	//Main currency of this account
	@ColumnInfo(name = "main_currency")
	val mainCurrency: String,

	//
	@ColumnInfo(name = "balance_in_main_currency")
	val balanceInMainCurrency: Double

) : BaseEntity
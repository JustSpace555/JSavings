package ru.jsavings.model.entity

import androidx.room.*
import ru.jsavings.model.converter.IdListConverter

@Entity
data class Account (

	//Account id
	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "account_id")
	val accountId: Int,

	//Account name
	val name: String,

	//All purses on account
	@ColumnInfo(name = "purses")
	@TypeConverters(IdListConverter::class)
	val pursesIdList: List<Int>
)
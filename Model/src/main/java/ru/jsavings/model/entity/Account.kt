package ru.jsavings.model.entity

import androidx.room.*

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
	@TypeConverters(PursesIdListConverter::class)
	val pursesIdList: List<Int>
) {
	companion object {

		class PursesIdListConverter {

			companion object {
				private const val PURSES_ID_SEPARATOR = ","
			}

			@TypeConverter
			fun pursesIdListToString(inputList: List<Int>): String = inputList.joinToString(PURSES_ID_SEPARATOR)

			@TypeConverter
			fun stringToPursesIdList(inputString: String): List<Int> =
				inputString.split(PURSES_ID_SEPARATOR).map { it.toInt() }
		}
	}
}
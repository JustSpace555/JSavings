package ru.jsavings.model.converter

import androidx.room.TypeConverter

class IdListConverter {

	companion object {
		private const val ID_SEPARATOR = ","
	}

	@TypeConverter
	fun idListToString(inputList: List<Int>): String = inputList.joinToString(ID_SEPARATOR)

	@TypeConverter
	fun stringToIdList(inputString: String): List<Int> =
		inputString.split(ID_SEPARATOR).map { it.toInt() }
}
package ru.jsavings.data.database.converters

import androidx.room.TypeConverter
import ru.jsavings.data.entity.category.CategoryType

class CategoryTypeConverter {

	@TypeConverter
	fun categoryToString(inputCategory: CategoryType): String = inputCategory.toString()

	@TypeConverter
	fun stringToCategory(inputString: String): CategoryType = CategoryType.valueOf(inputString)
}
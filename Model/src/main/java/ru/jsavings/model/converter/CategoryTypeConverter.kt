package ru.jsavings.model.converter

import androidx.room.TypeConverter
import ru.jsavings.model.entity.category.CategoryType

class CategoryTypeConverter {

	@TypeConverter
	fun categoryToString(inputCategory: CategoryType): String = inputCategory.toString()

	@TypeConverter
	fun stringToCategory(inputString: String): CategoryType = CategoryType.valueOf(inputString)
}
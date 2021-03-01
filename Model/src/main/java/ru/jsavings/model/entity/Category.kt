package ru.jsavings.model.entity

import androidx.room.*

@Entity
data class Category (

	//Category id
	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "category_id")
	val categoryId: Int,

	//Category name
	val name: String,

	//Category type
	@TypeConverters(CategoryTypeConverter::class)
	val type: CategoryType,

	//Category color (0xFFFFFFFF)
	val color: String,

	//Path to icon
	@ColumnInfo(name = "icon_path")
	val iconPath: String
) {
	companion object {
		enum class CategoryType {
			INCOME,
			CONSUMPTION,
			TRANSFER
		}

		class CategoryTypeConverter {

			@TypeConverter
			fun categoryToString(inputCategory: CategoryType): String = inputCategory.toString()

			@TypeConverter
			fun stringToCategory(inputString: String): CategoryType = CategoryType.valueOf(inputString)
		}
	}
}
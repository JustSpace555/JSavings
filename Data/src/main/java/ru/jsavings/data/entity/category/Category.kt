package ru.jsavings.data.entity.category

import androidx.room.*
import ru.jsavings.data.database.converters.CategoryTypeConverter

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
)
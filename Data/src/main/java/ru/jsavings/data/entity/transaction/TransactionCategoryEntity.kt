package ru.jsavings.data.entity.transaction

import androidx.room.*
import ru.jsavings.data.entity.BaseEntity

@Entity (tableName = "transaction_category_table")
data class TransactionCategoryEntity (

	//Category id
	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "category_id")
	val categoryId: Int,

	//Category name
	val name: String,

	//Category type (TransactionCategoryType)
	val type: String,

	//Category color (0xFFFFFF)
	val color: String,

	//Path to icon
	@ColumnInfo(name = "icon_path")
	val iconPath: String
) : BaseEntity
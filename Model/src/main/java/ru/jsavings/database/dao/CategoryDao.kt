package ru.jsavings.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import ru.jsavings.model.entity.category.Category

@Dao
interface CategoryDao {

	@Query("SELECT * FROM category")
	fun getAllCategories(): List<Category>

	@Query("SELECT * FROM category WHERE category_id = :id")
	fun getCategoryById(id: Int): Category

	@Update
	fun updateCategory(category: Category)

	@Delete
	fun deleteCategory(category: Category)
}
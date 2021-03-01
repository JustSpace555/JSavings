package ru.jsavings.database.dao

import androidx.room.Dao
import androidx.room.Query
import ru.jsavings.model.entity.Category

@Dao
interface CategoryDao {

	@Query("SELECT * FROM category")
	fun getAllCategories(): List<Category>

	@Query("SELECT * FROM category WHERE category_id = :id")
	fun getCategoryById(id: Int): Category
}
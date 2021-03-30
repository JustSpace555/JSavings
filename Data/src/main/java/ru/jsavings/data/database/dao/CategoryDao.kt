package ru.jsavings.data.database.dao

import androidx.room.*
import ru.jsavings.data.entity.category.Category

@Dao
interface CategoryDao {

	@Query("SELECT * FROM category WHERE category_id = :id")
	fun getCategoryById(id: Int): Category

	@Query("SELECT * FROM category WHERE category.category_id IN (:listIds)")
	fun getCategoriesByIdList(listIds: List<Int>): List<Category>

	@Insert
	fun addNewCategory(category: Category)

	@Update
	fun updateCategory(category: Category)

	@Delete
	fun deleteCategory(category: Category)
}
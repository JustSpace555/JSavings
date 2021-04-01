package ru.jsavings.data.database.dao

import androidx.room.*
import ru.jsavings.data.entity.transaction.TransactionCategoryEntity

@Dao
interface TransactionCategoryDao : BaseDao {

	@Query("SELECT * FROM transaction_category_table WHERE category_id = :inputId")
	fun getCategoryById(inputId: Int): TransactionCategoryEntity

	@Query("SELECT * FROM transaction_category_table WHERE type = :inputType")
	fun getCategoriesByTransactionType(inputType: String): List<TransactionCategoryEntity>

	@Insert
	fun addNewCategory(transactionCategoryEntity: TransactionCategoryEntity): Int

	@Update
	fun updateCategory(transactionCategoryEntity: TransactionCategoryEntity)

	@Delete
	fun deleteCategory(transactionCategoryEntity: TransactionCategoryEntity)
}
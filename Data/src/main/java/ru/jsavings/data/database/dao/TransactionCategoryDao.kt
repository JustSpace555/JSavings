package ru.jsavings.data.database.dao

import androidx.room.*
import ru.jsavings.data.entity.transaction.TransactionCategoryEntity

@Dao
internal interface TransactionCategoryDao : BaseDao {

	@Query("SELECT * FROM transaction_category_table WHERE category_name = :inputName")
	fun getCategoryByName(inputName: String): TransactionCategoryEntity

	@Query("SELECT * FROM transaction_category_table WHERE type = :inputType")
	fun getCategoriesByTransactionType(inputType: String): List<TransactionCategoryEntity>

	@Insert
	fun addNewCategory(transactionCategoryEntity: TransactionCategoryEntity)

	@Update
	fun updateCategory(transactionCategoryEntity: TransactionCategoryEntity)

	@Delete
	fun deleteCategory(transactionCategoryEntity: TransactionCategoryEntity)
}
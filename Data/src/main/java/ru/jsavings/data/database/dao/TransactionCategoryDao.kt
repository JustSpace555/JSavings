package ru.jsavings.data.database.dao

import androidx.room.*
import ru.jsavings.data.entity.database.transaction.TransactionCategoryEntity

@Dao
internal interface TransactionCategoryDao : BaseDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun addNewCategory(transactionCategoryEntity: TransactionCategoryEntity): Long

	@Update
	fun updateCategory(transactionCategoryEntity: TransactionCategoryEntity)

	@Delete
	fun deleteCategory(transactionCategoryEntity: TransactionCategoryEntity)
}
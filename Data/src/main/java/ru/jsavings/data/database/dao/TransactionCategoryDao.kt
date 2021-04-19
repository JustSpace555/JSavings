package ru.jsavings.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import ru.jsavings.data.entity.transaction.TransactionCategoryEntity

@Dao
internal interface TransactionCategoryDao : BaseDao {

	@Insert
	fun addNewCategory(transactionCategoryEntity: TransactionCategoryEntity)

	@Update
	fun updateCategory(transactionCategoryEntity: TransactionCategoryEntity)

	@Delete
	fun deleteCategory(transactionCategoryEntity: TransactionCategoryEntity)
}
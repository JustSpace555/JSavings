package ru.jsavings.data.database.dao.binding

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import ru.jsavings.data.entity.binding.CategoryWithTransactionsEntity

@Dao
internal interface CategoryWithTransactionsDao {

	@Transaction
	@Query("SELECT * FROM transaction_category_table")
	fun getCategoryWithTransactions(): List<CategoryWithTransactionsEntity>
}
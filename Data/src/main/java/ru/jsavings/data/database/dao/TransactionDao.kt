package ru.jsavings.data.database.dao

import androidx.room.*
import ru.jsavings.data.entity.database.transaction.TransactionEntity

@Dao
internal interface TransactionDao : BaseDao {

	@Query("SELECT * FROM transaction_table WHERE transaction_id = :inputId")
	fun getTransactionById(inputId: Int): TransactionEntity

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun addNewTransaction(transactionEntity: TransactionEntity): Long

	@Update
	fun updateTransaction(transactionEntity: TransactionEntity)

	@Delete
	fun deleteTransaction(transactionEntity: TransactionEntity)
}
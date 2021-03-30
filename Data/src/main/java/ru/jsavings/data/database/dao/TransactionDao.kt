package ru.jsavings.data.database.dao

import androidx.room.*
import ru.jsavings.data.entity.Transaction

@Dao
interface TransactionDao {

	@Query("SELECT * FROM `transaction` WHERE `transaction`.transaction_id = :id")
	fun getTransactionById(id: Int): Transaction

	@Query("SELECT * FROM `transaction` WHERE `transaction`.transaction_id IN (:listIds)")
	fun getTransactionsByIdList(listIds: List<Int>): List<Transaction>

	@Insert
	fun addNewTransaction(transaction: Transaction)

	@Update
	fun updateTransaction(transaction: Transaction)

	@Delete
	fun deleteTransaction(transaction: Transaction)
}
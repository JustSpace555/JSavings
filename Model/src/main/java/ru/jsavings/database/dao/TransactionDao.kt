package ru.jsavings.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import ru.jsavings.model.entity.Transaction

@Dao
interface TransactionDao {

	@Query("SELECT * FROM `transaction`")
	fun getAllTransactions(): List<Transaction>

	@Query("SELECT * FROM `transaction` WHERE `transaction`.transaction_id = :id")
	fun getTransactionById(id: Int): Transaction

	@Query("SELECT * FROM `transaction` WHERE `transaction`.transaction_id IN (:listIds)")
	fun getTransactionsByIdList(listIds: List<Int>): List<Transaction>

	@Update
	fun updateTransaction(transaction: Transaction)

	@Delete
	fun deleteTransaction(transaction: Transaction)
}
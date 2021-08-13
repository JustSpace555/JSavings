package ru.jsavings.data.database.dao

import androidx.room.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.entity.TransactionEntity

/**
 * Data access object to transactions' table
 *
 * @author JustSpace
 */
@Dao
internal interface TransactionDao : BaseDao {

	/**
	 * Get [TransactionEntity] by id
	 * @param transactionId id of transaction to get
	 * @return [Single] source with [TransactionEntity]
	 *
	 * @author JustSpace
	 */
	@Query("SELECT * FROM transaction_table WHERE transaction_id = :transactionId")
	fun getTransactionById(transactionId: Int): Single<TransactionEntity>

	/**
	 * Insert new transaction to transactions' table
	 * @param transactionEntity [TransactionEntity] to insert
	 * @return [Single] source with id of inserted transaction
	 *
	 * @author JustSpace
	 */
	@Insert
	fun insertNewTransaction(transactionEntity: TransactionEntity): Single<Long>

	/**
	 * Update transaction in transactions' table
	 * @param transactionEntity [TransactionEntity] with id which must be updated
	 * @return [Completable] source of action
	 *
	 * @author JustSpace
	 */
	@Update
	fun updateTransaction(transactionEntity: TransactionEntity): Completable

	/**
	 * Delete transaction from transactions' table
	 * @param transactionEntity [TransactionEntity] which must be deleted
	 * @return [Completable] source of action
	 *
	 * @author JustSpace
	 */
	@Delete
	fun deleteTransactionById(transactionEntity: TransactionEntity): Completable
}
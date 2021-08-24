package ru.jsavings.data.database.dao

import androidx.room.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.entity.database.TransactionEntity

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
	fun getTransactionById(transactionId: Long): Single<TransactionEntity>

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
	fun deleteTransaction(transactionEntity: TransactionEntity): Completable

	/**
	 * Get transaction from transactions' table by account id to which they belong and in certain time period
	 * @param accountId Id of account
	 * @param startTime Start time of time period
	 * @param endTime End time of start period. Must be less or equal to [startTime]
	 * @return [Single] source of list with transactions
	 *
	 * @author JustSpace
	 */
	@Query("SELECT * FROM transaction_table WHERE account_fk_id = :accountId AND date BETWEEN :startTime AND :endTime ORDER BY date DESC")
	fun getTransactionsByAccountIdAndTime(accountId: Long, startTime: Long, endTime: Long): Single<List<TransactionEntity>>

	/**
	 * Get date of last transaction by it's account id
	 * @param accountId Id of Account
	 * @return [Maybe] source of date's representation in [Long]. Transactions' table may be empty
	 *
	 * @author Михаил Мошков
	 */
	@Query("SELECT MAX(date) FROM transaction_table WHERE account_fk_id = :accountId")
	fun getLastTransactionDateByAccountId(accountId: Long): Maybe<Long>
}
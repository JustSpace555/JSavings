package ru.jsavings.data.database.dao

import androidx.room.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.entity.database.TransactionEntity
import ru.jsavings.data.entity.database.TransactionGroupEntity

/**
 * Data access object to transactions' table
 *
 * @author JustSpace
 */
@Dao
internal interface TransactionDao : BaseDao {

	/**
	 * Get [TransactionGroupEntity] by [TransactionEntity.transactionId]
	 * @param transactionId id of transaction to get
	 * @return [Single] source with [TransactionGroupEntity]
	 *
	 * @author JustSpace
	 */
	@Transaction
	@Query("SELECT * FROM transaction_table WHERE transaction_id = :transactionId")
	fun getTransactionById(transactionId: Long): Single<TransactionGroupEntity>

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
	 * Get all transactions from database by account id sorted by date
	 * @param accountId Id of account
	 * @return [Flowable] source of action with list of [TransactionGroupEntity]. [Flowable] is needed for live updates
	 * of transactions list
	 *
	 * @author JustSpace
	 */
	@Transaction
	@Query("SELECT * FROM transaction_table WHERE account_fk_id = :accountId ORDER BY date_day DESC, date_time DESC")
	fun getAllTransactionsByAccountId(accountId: Long): Flowable<List<TransactionGroupEntity>>
}
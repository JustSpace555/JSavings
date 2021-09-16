package ru.jsavings.data.repository.database.transaction

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.entity.database.TransactionEntity
import ru.jsavings.data.entity.database.TransactionGroupEntity
import ru.jsavings.data.repository.database.common.BaseDbRepository

/**
 * Repository that implements all [ru.jsavings.data.database.dao.TransactionDao] requests
 *
 * @author JustSpace
 */
interface TransactionRepository : BaseDbRepository {

	/**
	 * Get [TransactionEntity] by id
	 * @param transactionId id of transaction to get
	 * @return [Single] source with [TransactionGroupEntity]
	 *
	 * @author JustSpace
	 */
	fun getTransactionById(transactionId: Long): Single<TransactionGroupEntity>

	/**
	 * Insert new transaction to transactions' table
	 * @param transactionEntity [TransactionEntity] to insert
	 * @return [Single] source with id of inserted transaction
	 *
	 * @author JustSpace
	 */
	fun insertNewTransaction(transactionEntity: TransactionEntity): Single<Long>

	/**
	 * Update transaction in transactions' table
	 * @param transactionEntity [TransactionEntity] with id which must be updated
	 * @return [Completable] source of action
	 *
	 * @author JustSpace
	 */
	fun updateTransaction(transactionEntity: TransactionEntity): Completable

	/**
	 * Delete transaction from transactions' table by it's id
	 * @param transactionId Id of transaction that must be deleted
	 * @return [Completable] source of action
	 *
	 * @author JustSpace
	 */
	fun deleteTransactionById(transactionId: Long): Completable

	/**
	 * Get all transactions from database by account id
	 * @param accountId Id of account
	 * @return [Flowable] source of action with list of [TransactionGroupEntity]
	 *
	 * @author JustSpace
	 */
	fun getAllTransactionsByAccountId(accountId: Long): Flowable<List<TransactionGroupEntity>>
}
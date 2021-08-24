package ru.jsavings.data.repository.database.transaction

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.database.dao.TransactionDao
import ru.jsavings.data.entity.database.TransactionEntity

/**
 * Implementation of [TransactionRepository]
 * @param dao [TransactionDao] to get data from
 *
 * @author JustSpace
 */
internal class TransactionRepositoryImpl(override val dao: TransactionDao) : TransactionRepository {

	override fun getTransactionById(transactionId: Long): Single<TransactionEntity> =
		dao.getTransactionById(transactionId)

	override fun insertNewTransaction(transactionEntity: TransactionEntity): Single<Long> =
		dao.insertNewTransaction(transactionEntity)

	override fun updateTransaction(transactionEntity: TransactionEntity): Completable =
		dao.updateTransaction(transactionEntity)

	override fun deleteTransaction(transactionEntity: TransactionEntity): Completable =
		dao.deleteTransaction(transactionEntity)

	override fun getTransactionsByAccountIdAndTime(
		accountId: Long,
		startTime: Long,
		endTime: Long
	): Single<List<TransactionEntity>> = dao.getTransactionsByAccountIdAndTime(accountId, startTime, endTime)

	override fun getLastTransactionDateByAccountId(accountId: Long): Maybe<Long> =
		dao.getLastTransactionDateByAccountId(accountId)
}
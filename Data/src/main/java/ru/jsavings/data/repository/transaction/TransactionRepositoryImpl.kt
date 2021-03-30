package ru.jsavings.data.repository.transaction

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.database.dao.TransactionDao
import ru.jsavings.data.entity.Transaction
import java.lang.Exception

class TransactionRepositoryImpl(private val transactionDao: TransactionDao) : TransactionRepository {

	override fun getTransactionById(id: Int): Single<Transaction> = Single.create { subscriber ->
		try {
			subscriber.onSuccess(transactionDao.getTransactionById(id))
		} catch (e: Exception) {
			subscriber.onError(e)
		}
	}

	override fun getTransactionsByIdList(listIds: List<Int>): Single<List<Transaction>> = Single.create { subscriber ->
		try {
			subscriber.onSuccess(transactionDao.getTransactionsByIdList(listIds))
		} catch (e: Exception) {
			subscriber.onError(e)
		}
	}

	override fun addNewTransaction(transaction: Transaction): Completable = Completable.create { subscriber ->
		try {
			transactionDao.addNewTransaction(transaction)
			subscriber.onComplete()
		} catch (e: Exception) {
			subscriber.onError(e)
		}
	}

	override fun updateTransaction(transaction: Transaction): Completable = Completable.create { subscriber ->
		try {
			transactionDao.updateTransaction(transaction)
			subscriber.onComplete()
		} catch (e: Exception) {
			subscriber.onError(e)
		}
	}

	override fun deleteTransaction(transaction: Transaction): Completable = Completable.create { subscriber ->
		try {
			transactionDao.deleteTransaction(transaction)
			subscriber.onComplete()
		} catch (e: Exception) {
			subscriber.onError(e)
		}
	}
}
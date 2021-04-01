package ru.jsavings.data.repository.transaction

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.database.dao.BaseDao
import ru.jsavings.data.database.dao.TransactionDao
import ru.jsavings.data.entity.transaction.TransactionEntity
import ru.jsavings.data.mappers.BaseMapper
import ru.jsavings.data.mappers.transaction.TransactionMapper
import ru.jsavings.data.model.transaction.Transaction
import java.lang.Exception

class TransactionRepositoryImpl(
	override val dao: TransactionDao,
	override val mapper: TransactionMapper
) : TransactionRepository {

	override fun getTransactionById(id: Int): Single<Transaction> = Single.create { subscriber ->
		try {
			subscriber.onSuccess(
				mapper.mapEntityToModel(dao.getTransactionById(id))
			)
		} catch (e: Exception) {
			subscriber.onError(e)
		}
	}

	override fun addNewTransaction(transaction: Transaction): Single<Int> =
		Single.create { subscriber ->
			try {
				subscriber.onSuccess(
					dao.addNewTransaction(mapper.mapModelToEntity(transaction))
				)
			} catch (e: Exception) {
				subscriber.onError(e)
			}
		}

	override fun updateTransaction(transaction: Transaction): Completable =
		Completable.create { subscriber ->
			try {
				dao.updateTransaction(mapper.mapModelToEntity(transaction))
				subscriber.onComplete()
			} catch (e: Exception) {
				subscriber.onError(e)
			}
		}

	override fun deleteTransaction(transaction: Transaction): Completable =
		Completable.create { subscriber ->
			try {
				dao.deleteTransaction(mapper.mapModelToEntity(transaction))
				subscriber.onComplete()
			} catch (e: Exception) {
				subscriber.onError(e)
			}
		}
}
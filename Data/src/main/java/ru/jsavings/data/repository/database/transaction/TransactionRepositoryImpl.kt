package ru.jsavings.data.repository.database.transaction

import io.reactivex.rxjava3.core.Completable
import ru.jsavings.data.database.dao.TransactionDao
import ru.jsavings.data.mappers.database.transaction.TransactionMapper
import ru.jsavings.data.model.database.transaction.Transaction

internal class TransactionRepositoryImpl(
	override val dao: TransactionDao,
	override val mapper: TransactionMapper
) : TransactionRepository {

	override fun addNewTransaction(transaction: Transaction): Completable =
		Completable.create { subscriber ->
			try {
				dao.addNewTransaction(mapper.mapModelToEntity(transaction))
				subscriber.onComplete()
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
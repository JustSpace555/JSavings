package ru.jsavings.data.repository.transaction

import io.reactivex.rxjava3.core.Completable
import ru.jsavings.data.database.dao.TransactionDao
import ru.jsavings.domain.usecase.mappers.transaction.TransactionMapper
import ru.jsavings.domain.usecase.model.transaction.Transaction

internal class TransactionRepositoryImpl(
	override val dao: TransactionDao,
	override val mapper: ru.jsavings.domain.usecase.mappers.transaction.TransactionMapper
) : TransactionRepository {

	override fun addNewTransaction(transaction: ru.jsavings.domain.usecase.model.transaction.Transaction): Completable =
		Completable.create { subscriber ->
			try {
				dao.insertNewTransaction(mapper.mapModelToEntity(transaction))
				subscriber.onComplete()
			} catch (e: Exception) {
				subscriber.onError(e)
			}
		}

	override fun updateTransaction(transaction: ru.jsavings.domain.usecase.model.transaction.Transaction): Completable =
		Completable.create { subscriber ->
			try {
				dao.updateTransaction(mapper.mapModelToEntity(transaction))
				subscriber.onComplete()
			} catch (e: Exception) {
				subscriber.onError(e)
			}
		}

	override fun deleteTransaction(transaction: ru.jsavings.domain.usecase.model.transaction.Transaction): Completable =
		Completable.create { subscriber ->
			try {
				dao.deleteTransactionById(mapper.mapModelToEntity(transaction))
				subscriber.onComplete()
			} catch (e: Exception) {
				subscriber.onError(e)
			}
		}
}
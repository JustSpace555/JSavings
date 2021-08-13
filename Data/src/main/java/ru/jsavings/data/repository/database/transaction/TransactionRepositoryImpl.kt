package ru.jsavings.data.repository.database.transaction

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.database.dao.TransactionDao
<<<<<<< refs/remotes/origin/dev:Data/src/main/java/ru/jsavings/data/repository/database/transaction/TransactionRepositoryImpl.kt
import ru.jsavings.data.mappers.database.transaction.TransactionMapper
import ru.jsavings.data.model.database.transaction.Transaction
=======
import ru.jsavings.domain.usecase.mappers.transaction.TransactionMapper
import ru.jsavings.domain.usecase.model.transaction.Transaction
>>>>>>> Rework started:Data/src/main/java/ru/jsavings/data/repository/transaction/TransactionRepositoryImpl.kt

internal class TransactionRepositoryImpl(
	override val dao: TransactionDao,
	override val mapper: ru.jsavings.domain.usecase.mappers.transaction.TransactionMapper
) : TransactionRepository {

<<<<<<< refs/remotes/origin/dev:Data/src/main/java/ru/jsavings/data/repository/database/transaction/TransactionRepositoryImpl.kt
	override fun addNewTransaction(transaction: Transaction): Single<Long> =
		Single.create { subscriber ->
			try {
				val id = dao.addNewTransaction(mapper.mapModelToEntity(transaction))
				subscriber.onSuccess(id)
=======
	override fun addNewTransaction(transaction: ru.jsavings.domain.usecase.model.transaction.Transaction): Completable =
		Completable.create { subscriber ->
			try {
				dao.insertNewTransaction(mapper.mapModelToEntity(transaction))
				subscriber.onComplete()
>>>>>>> Rework started:Data/src/main/java/ru/jsavings/data/repository/transaction/TransactionRepositoryImpl.kt
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
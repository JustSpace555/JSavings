package ru.jsavings.data.repository.database.transaction

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.model.database.transaction.Transaction
import ru.jsavings.data.repository.database.common.BaseDbRepository

interface TransactionRepository : BaseDbRepository {

	fun addNewTransaction(transaction: Transaction): Single<Long>

	fun updateTransaction(transaction: Transaction): Completable

	fun deleteTransaction(transaction: Transaction): Completable
}
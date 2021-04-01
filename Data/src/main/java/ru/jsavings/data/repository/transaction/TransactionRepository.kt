package ru.jsavings.data.repository.transaction

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.entity.transaction.TransactionEntity
import ru.jsavings.data.model.transaction.Transaction
import ru.jsavings.data.repository.BaseRepository

interface TransactionRepository : BaseRepository<TransactionEntity, Transaction> {

	fun getTransactionById(id: Int): Single<Transaction>

	fun addNewTransaction(transaction: Transaction): Single<Int>

	fun updateTransaction(transaction: Transaction): Completable

	fun deleteTransaction(transaction: Transaction): Completable
}
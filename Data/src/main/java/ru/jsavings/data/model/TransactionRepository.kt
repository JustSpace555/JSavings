package ru.jsavings.data.model

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.entities.Transaction

interface TransactionRepository {

	fun getTransactionById(id: Int): Single<Transaction>

	fun getTransactionsByIdList(listIds: List<Int>): Single<List<Transaction>>

	fun addNewTransaction(transaction: Transaction): Completable

	fun updateTransaction(transaction: Transaction): Completable

	fun deleteTransaction(transaction: Transaction): Completable
}
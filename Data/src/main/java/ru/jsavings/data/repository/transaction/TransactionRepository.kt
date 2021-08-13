package ru.jsavings.data.repository.transaction

import io.reactivex.rxjava3.core.Completable
import ru.jsavings.domain.usecase.model.transaction.Transaction
import ru.jsavings.data.repository.common.DbRepository

interface TransactionRepository : DbRepository {

	fun addNewTransaction(transaction: ru.jsavings.domain.usecase.model.transaction.Transaction): Completable

	fun updateTransaction(transaction: ru.jsavings.domain.usecase.model.transaction.Transaction): Completable

	fun deleteTransaction(transaction: ru.jsavings.domain.usecase.model.transaction.Transaction): Completable
}
package ru.jsavings.data.repository.database.transaction.caterory

import io.reactivex.rxjava3.core.Completable
import ru.jsavings.data.model.database.transaction.TransactionCategory
import ru.jsavings.data.repository.database.common.BaseDbRepository

interface TransactionCategoryRepository : BaseDbRepository {

	fun addNewCategory(transactionCategory: TransactionCategory): Completable

	fun updateCategory(transactionCategory: TransactionCategory): Completable

	fun deleteCategory(transactionCategory: TransactionCategory): Completable
}
package ru.jsavings.data.repository.transaction.caterory

import io.reactivex.rxjava3.core.Completable
import ru.jsavings.data.model.transaction.TransactionCategory
import ru.jsavings.data.repository.common.DbRepository

interface TransactionCategoryRepository : DbRepository {

	fun addNewCategory(transactionCategory: TransactionCategory): Completable

	fun updateCategory(transactionCategory: TransactionCategory): Completable

	fun deleteCategory(transactionCategory: TransactionCategory): Completable
}
package ru.jsavings.data.repository.transaction.caterory

import io.reactivex.rxjava3.core.Completable
import ru.jsavings.domain.usecase.model.transaction.TransactionCategory
import ru.jsavings.data.repository.common.DbRepository

interface TransactionCategoryRepository : DbRepository {

	fun addNewCategory(transactionCategory: ru.jsavings.domain.usecase.model.transaction.TransactionCategory): Completable

	fun updateCategory(transactionCategory: ru.jsavings.domain.usecase.model.transaction.TransactionCategory): Completable

	fun deleteCategory(transactionCategory: ru.jsavings.domain.usecase.model.transaction.TransactionCategory): Completable
}
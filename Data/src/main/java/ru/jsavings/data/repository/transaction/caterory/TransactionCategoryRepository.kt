package ru.jsavings.data.repository.transaction.caterory

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.entity.transaction.TransactionCategoryEntity
import ru.jsavings.data.model.transaction.category.TransactionCategory
import ru.jsavings.data.repository.BaseRepository

interface TransactionCategoryRepository : BaseRepository<TransactionCategoryEntity, TransactionCategory> {

	fun getCategoryById(id: Int): Single<TransactionCategory>

	fun addNewCategory(transactionCategory: TransactionCategory): Single<Int>

	fun updateCategory(transactionCategory: TransactionCategory): Completable

	fun deleteCategory(transactionCategory: TransactionCategory): Completable
}
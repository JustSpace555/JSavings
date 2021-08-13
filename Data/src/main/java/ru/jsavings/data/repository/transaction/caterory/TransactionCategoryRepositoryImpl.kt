package ru.jsavings.data.repository.transaction.caterory

import io.reactivex.rxjava3.core.Completable
import ru.jsavings.data.database.dao.TransactionCategoryDao
import ru.jsavings.domain.usecase.mappers.transaction.TransactionCategoryMapper
import ru.jsavings.domain.usecase.model.transaction.TransactionCategory

internal class TransactionCategoryRepositoryImpl (
	override val dao: TransactionCategoryDao,
	override val mapper: ru.jsavings.domain.usecase.mappers.transaction.TransactionCategoryMapper
) : TransactionCategoryRepository {

	override fun addNewCategory(transactionCategory: ru.jsavings.domain.usecase.model.transaction.TransactionCategory): Completable =
		Completable.create { subscriber ->
			try {
				dao.insertNewCategory(mapper.mapModelToEntity(transactionCategory))
				subscriber.onComplete()
			} catch (e: Exception) {
				subscriber.onError(e)
			}
		}

	override fun updateCategory(transactionCategory: ru.jsavings.domain.usecase.model.transaction.TransactionCategory): Completable =
		Completable.create { subscriber ->
			try {
				dao.updateCategory(mapper.mapModelToEntity(transactionCategory))
				subscriber.onComplete()
			} catch (e: Exception) {
				subscriber.onError(e)
			}
		}

	override fun deleteCategory(transactionCategory: ru.jsavings.domain.usecase.model.transaction.TransactionCategory): Completable =
		Completable.create { subscriber ->
			try {
				dao.deleteCategoryById(mapper.mapModelToEntity(transactionCategory))
				subscriber.onComplete()
			} catch (e: Exception) {
				subscriber.onError(e)
			}
		}
}
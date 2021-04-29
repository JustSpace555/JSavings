package ru.jsavings.data.repository.database.transaction.caterory

import io.reactivex.rxjava3.core.Completable
import ru.jsavings.data.database.dao.TransactionCategoryDao
import ru.jsavings.data.mappers.database.transaction.TransactionCategoryMapper
import ru.jsavings.data.model.database.transaction.TransactionCategory

internal class TransactionCategoryRepositoryImpl (
	override val dao: TransactionCategoryDao,
	override val mapper: TransactionCategoryMapper
) : TransactionCategoryRepository {

	override fun addNewCategory(transactionCategory: TransactionCategory): Completable =
		Completable.create { subscriber ->
			try {
				dao.addNewCategory(mapper.mapModelToEntity(transactionCategory))
				subscriber.onComplete()
			} catch (e: Exception) {
				subscriber.onError(e)
			}
		}

	override fun updateCategory(transactionCategory: TransactionCategory): Completable =
		Completable.create { subscriber ->
			try {
				dao.updateCategory(mapper.mapModelToEntity(transactionCategory))
				subscriber.onComplete()
			} catch (e: Exception) {
				subscriber.onError(e)
			}
		}

	override fun deleteCategory(transactionCategory: TransactionCategory): Completable =
		Completable.create { subscriber ->
			try {
				dao.deleteCategory(mapper.mapModelToEntity(transactionCategory))
				subscriber.onComplete()
			} catch (e: Exception) {
				subscriber.onError(e)
			}
		}
}
package ru.jsavings.data.repository.transaction.caterory

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.database.dao.TransactionCategoryDao
import ru.jsavings.data.mappers.transaction.TransactionCategoryMapper
import ru.jsavings.data.model.transaction.category.TransactionCategory
import java.lang.Exception

class TransactionCategoryRepositoryImpl (
	override val dao: TransactionCategoryDao,
	override val mapper: TransactionCategoryMapper
) : TransactionCategoryRepository {

	override fun getCategoryById(id: Int): Single<TransactionCategory> = Single.create { subscriber ->
		try {
			subscriber.onSuccess(
				mapper.mapEntityToModel(dao.getCategoryById(id))
			)
		} catch (e: Exception) {
			subscriber.onError(e)
		}
	}

	override fun addNewCategory(transactionCategory: TransactionCategory): Single<Int> =
		Single.create { subscriber ->
			try {
				subscriber.onSuccess(
					dao.addNewCategory(mapper.mapModelToEntity(transactionCategory))
				)
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
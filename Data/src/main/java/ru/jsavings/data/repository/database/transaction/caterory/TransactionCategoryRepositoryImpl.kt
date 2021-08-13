package ru.jsavings.data.repository.database.transaction.caterory

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.database.dao.TransactionCategoryDao
<<<<<<< refs/remotes/origin/dev:Data/src/main/java/ru/jsavings/data/repository/database/transaction/caterory/TransactionCategoryRepositoryImpl.kt
import ru.jsavings.data.mappers.database.transaction.TransactionCategoryMapper
import ru.jsavings.data.model.database.transaction.TransactionCategory
=======
import ru.jsavings.domain.usecase.mappers.transaction.TransactionCategoryMapper
import ru.jsavings.domain.usecase.model.transaction.TransactionCategory
>>>>>>> Rework started:Data/src/main/java/ru/jsavings/data/repository/transaction/caterory/TransactionCategoryRepositoryImpl.kt

internal class TransactionCategoryRepositoryImpl (
	override val dao: TransactionCategoryDao,
	override val mapper: ru.jsavings.domain.usecase.mappers.transaction.TransactionCategoryMapper
) : TransactionCategoryRepository {

<<<<<<< refs/remotes/origin/dev:Data/src/main/java/ru/jsavings/data/repository/database/transaction/caterory/TransactionCategoryRepositoryImpl.kt
	override fun addNewCategory(transactionCategory: TransactionCategory): Single<Long> =
		Single.create { subscriber ->
			try {
				val id = dao.addNewCategory(mapper.mapModelToEntity(transactionCategory))
				subscriber.onSuccess(id)
=======
	override fun addNewCategory(transactionCategory: ru.jsavings.domain.usecase.model.transaction.TransactionCategory): Completable =
		Completable.create { subscriber ->
			try {
				dao.insertNewCategory(mapper.mapModelToEntity(transactionCategory))
				subscriber.onComplete()
>>>>>>> Rework started:Data/src/main/java/ru/jsavings/data/repository/transaction/caterory/TransactionCategoryRepositoryImpl.kt
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
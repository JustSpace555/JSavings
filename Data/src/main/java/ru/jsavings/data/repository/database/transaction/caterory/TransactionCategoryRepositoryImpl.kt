package ru.jsavings.data.repository.database.transaction.caterory

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.database.dao.TransactionCategoryDao
import ru.jsavings.data.entity.database.TransactionCategoryEntity

/**
 * Implementation of [TransactionCategoryRepository]
 * @param dao [TransactionCategoryDao] to get data from
 * @author JustSpace
 */
internal class TransactionCategoryRepositoryImpl(
	override val dao: TransactionCategoryDao
) : TransactionCategoryRepository {

	override fun getCategoryById(transactionCategoryId: Long): Single<TransactionCategoryEntity> =
		dao.getCategoryById(transactionCategoryId)

	override fun insertNewCategory(transactionCategoryEntity: TransactionCategoryEntity): Single<Long> =
		dao.insertNewCategory(transactionCategoryEntity)

	override fun deleteCategory(transactionCategoryEntity: TransactionCategoryEntity): Completable =
		dao.deleteCategory(transactionCategoryEntity)

	override fun getCategoriesByAccountId(accountId: Long): Single<List<TransactionCategoryEntity>> =
		dao.getCategoriesByAccountId(accountId)
}
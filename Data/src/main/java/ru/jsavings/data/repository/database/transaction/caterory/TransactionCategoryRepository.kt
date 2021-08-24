package ru.jsavings.data.repository.database.transaction.caterory

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.entity.database.TransactionCategoryEntity
import ru.jsavings.data.repository.database.common.BaseDbRepository

/**
 * Repository that implements all [ru.jsavings.data.database.dao.TransactionCategoryDao] requests
 * @author JustSpace
 */
interface TransactionCategoryRepository : BaseDbRepository {

	/**
	 * Add new category to transactions category table
	 * @param transactionCategoryEntity [TransactionCategoryEntity] which must be insert
	 * @return [Single] source with id of new category
	 *
	 * @author JustSpace
	 */
	fun insertNewCategory(transactionCategoryEntity: TransactionCategoryEntity): Single<Long>

	/**
	 * Get transaction category from database by it id
	 * @param transactionCategoryId Id of category
	 * @return [Single] source with [TransactionCategoryEntity]
	 *
	 * @author JustSpace
	 */
	fun getCategoryById(transactionCategoryId: Long): Single<TransactionCategoryEntity>

	/**
	 * Delete transaction category from transaction categories' table
	 * @param transactionCategoryEntity [TransactionCategoryEntity] which must be deleted
	 * @return [Completable] source of action
	 *
	 * @author JustSpace
	 */
	fun deleteCategory(transactionCategoryEntity: TransactionCategoryEntity): Completable
}
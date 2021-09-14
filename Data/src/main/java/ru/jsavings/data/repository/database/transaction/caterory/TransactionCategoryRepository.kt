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
	 * @param transactionCategoryId Id of category that must be deleted
	 * @return [Completable] source of action
	 *
	 * @author JustSpace
	 */
	fun deleteCategoryById(transactionCategoryId: Long): Completable

	/**
	 * Get all transaction categories from database by account id to which they belongs
	 * @param accountId Id of account
	 * @return [Single] source with list of [TransactionCategoryEntity]
	 *
	 * @author Михаил Мошков
	 */
	fun getCategoriesByAccountId(accountId: Long): Single<List<TransactionCategoryEntity>>

	/**
	 * Update transaction category
	 * @param transactionCategory [TransactionCategoryEntity] to update with new values except for transaction category id
	 * @return [Completable] source of action
	 *
	 * @author JustSpace
	 */
	fun updateCategory(transactionCategory: TransactionCategoryEntity): Completable
}
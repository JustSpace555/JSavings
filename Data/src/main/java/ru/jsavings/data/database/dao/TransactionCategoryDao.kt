package ru.jsavings.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.entity.database.TransactionCategoryEntity

/**
 * Data access object for transaction category
 *
 * @author JustSpace
 */
@Dao
internal interface TransactionCategoryDao : BaseDao {

	/**
	 * Get transaction category from database by it id
	 * @param categoryId Id of category
	 * @return [Single] source with [TransactionCategoryEntity]
	 *
	 * @author JustSpace
	 */
	@Query("SELECT * FROM transaction_category_table WHERE category_id = :categoryId")
	fun getCategoryById(categoryId: Long): Single<TransactionCategoryEntity>

	/**
	 * Add new category to transactions category table
	 * @param transactionCategoryEntity [TransactionCategoryEntity] which must be insert
	 * @return [Single] source with id of new category
	 *
	 * @author JustSpace
	 */
	@Insert
	fun insertNewCategory(transactionCategoryEntity: TransactionCategoryEntity): Single<Long>

	/**
	 * Delete transaction category from transaction categories' table
	 * @param transactionCategoryEntity [TransactionCategoryEntity] which must be deleted
	 * @return [Completable] source of action
	 *
	 * @author JustSpace
	 */
	@Delete
	fun deleteCategory(transactionCategoryEntity: TransactionCategoryEntity): Completable

	/**
	 * Get all transaction categories from database by account id to which they belongs
	 * @param accountId Id of account
	 * @return [Single] source with list of [TransactionCategoryEntity]
	 *
	 * @author Михаил Мошков
	 */
	@Query("SELECT * FROM transaction_category_table WHERE account_fk_id = :accountId")
	fun getCategoriesByAccountId(accountId: Long): Single<List<TransactionCategoryEntity>>
}
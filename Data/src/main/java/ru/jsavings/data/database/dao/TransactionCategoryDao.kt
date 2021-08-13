package ru.jsavings.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.entity.TransactionCategoryEntity

/**
 * Data access object for transaction category
 *
 * @author JustSpace
 */
@Dao
internal interface TransactionCategoryDao : BaseDao {

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
	fun deleteCategoryById(transactionCategoryEntity: TransactionCategoryEntity): Completable
}
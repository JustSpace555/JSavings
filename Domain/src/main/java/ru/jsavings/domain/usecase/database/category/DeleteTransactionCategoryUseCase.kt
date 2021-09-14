package ru.jsavings.domain.usecase.database.category

import io.reactivex.rxjava3.core.Completable
import ru.jsavings.data.repository.database.transaction.caterory.TransactionCategoryRepository
import ru.jsavings.domain.usecase.common.BaseUseCase

/**
 * UseCase for deleting transaction category from database
 * @param transactionCategoryRepository [TransactionCategoryRepository] Repository to interact with
 *
 * @author JustSpace
 */
class DeleteTransactionCategoryUseCase(
	private val transactionCategoryRepository: TransactionCategoryRepository
) : BaseUseCase {

	/**
	 * Invoke usecase
	 * @param categoryId Id of category that must be deleted
	 * @return [Completable] source of action
	 *
	 * @author JustSpace
	 */
	operator fun invoke(categoryId: Long): Completable = transactionCategoryRepository.deleteCategoryById(categoryId)
}
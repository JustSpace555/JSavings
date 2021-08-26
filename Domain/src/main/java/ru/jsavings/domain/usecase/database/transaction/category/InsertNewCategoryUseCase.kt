package ru.jsavings.domain.usecase.database.transaction.category

import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.repository.database.transaction.caterory.TransactionCategoryRepository
import ru.jsavings.domain.mappers.database.TransactionCategoryMapper
import ru.jsavings.domain.model.database.transaction.category.TransactionCategory
import ru.jsavings.domain.usecase.common.BaseUseCase

/**
 * UseCase for inserting new category to database
 * @param transactionCategoryRepository [TransactionCategoryRepository] for interacting with database
 * @param transactionCategoryMapper [TransactionCategoryMapper] to map model to entity
 *
 * @author Михаил Мошков
 */
class InsertNewCategoryUseCase(
	private val transactionCategoryRepository: TransactionCategoryRepository,
	private val transactionCategoryMapper: TransactionCategoryMapper
) : BaseUseCase {

	/**
	 * Invoke usecase
	 * @param category [TransactionCategory] to insert
	 * @return [Single] source with id of new category from database
	 *
	 * @author Михаил Мошков
	 */
	operator fun invoke(category: TransactionCategory): Single<Long> = transactionCategoryRepository.insertNewCategory(
		transactionCategoryMapper.mapModelToEntity(category)
	)
}
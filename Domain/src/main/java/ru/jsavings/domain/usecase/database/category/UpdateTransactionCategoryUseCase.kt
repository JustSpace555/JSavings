package ru.jsavings.domain.usecase.database.category

import io.reactivex.rxjava3.core.Completable
import ru.jsavings.data.repository.database.transaction.caterory.TransactionCategoryRepository
import ru.jsavings.domain.mappers.database.TransactionCategoryMapper
import ru.jsavings.domain.model.database.category.TransactionCategory
import ru.jsavings.domain.usecase.common.BaseUseCase

/**
 * Usecase for updating transaction category
 * @param transactionCategoryRepository [TransactionCategoryRepository] to interact with database
 * @param transactionCategoryMapper [TransactionCategoryMapper] to map model to entity
 *
 * @author JustSpace
 */
class UpdateTransactionCategoryUseCase(
	private val transactionCategoryRepository: TransactionCategoryRepository,
	private val transactionCategoryMapper: TransactionCategoryMapper
) : BaseUseCase {

	operator fun invoke(transactionCategory: TransactionCategory): Completable =
		transactionCategoryRepository.updateCategory(transactionCategoryMapper.mapModelToEntity(transactionCategory))
}
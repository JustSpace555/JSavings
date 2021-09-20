package ru.jsavings.domain.usecase.database.category

import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.repository.database.transaction.caterory.TransactionCategoryRepository
import ru.jsavings.domain.mappers.database.TransactionCategoryMapper
import ru.jsavings.domain.model.database.category.TransactionCategory
import ru.jsavings.domain.usecase.common.BaseUseCase

/**
 * UseCase for getting transaction category by it id
 *
 * @author JustSpace
 */
class GetTransactionCategoryByIdUseCase(
	private val transactionCategoryRepository: TransactionCategoryRepository,
	private val transactionCategoryMapper: TransactionCategoryMapper
) : BaseUseCase() {

	operator fun invoke(transactionId: Long): Single<TransactionCategory> = transactionCategoryRepository
		.getCategoryById(transactionId)
		.map { categoryEntity -> transactionCategoryMapper.mapEntityToModel(categoryEntity) }
}
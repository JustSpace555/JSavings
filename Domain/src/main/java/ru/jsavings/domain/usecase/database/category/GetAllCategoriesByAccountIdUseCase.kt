package ru.jsavings.domain.usecase.database.category

import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.repository.database.transaction.caterory.TransactionCategoryRepository
import ru.jsavings.domain.mappers.database.TransactionCategoryMapper
import ru.jsavings.domain.model.database.category.TransactionCategory
import ru.jsavings.domain.usecase.common.BaseUseCase

/**
 * UseCase for getting all transaction categories from database
 * @param transactionCategoryRepository [TransactionCategoryRepository] repository to interact with
 * @param transactionCategoryMapper [TransactionCategoryMapper] for mapping entity to model
 *
 * @author Михаил Мошков
 */
class GetAllCategoriesByAccountIdUseCase(
	private val transactionCategoryRepository: TransactionCategoryRepository,
	private val transactionCategoryMapper: TransactionCategoryMapper
) : BaseUseCase {

	/**
	 * Invoking usecase
	 * @param accountId Id of account
	 * @return [Single] source of list with [TransactionCategory]
	 *
	 * @author Михаил Мошков
	 */
	operator fun invoke(accountId: Long): Single<List<TransactionCategory>> = transactionCategoryRepository
		.getCategoriesByAccountId(accountId)
		.map { entitiesList ->
			entitiesList.map { entity ->
				transactionCategoryMapper.mapEntityToModel(entity)
			}
		}
}
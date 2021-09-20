package ru.jsavings.domain.usecase.database.transaction

import android.annotation.SuppressLint
import io.reactivex.rxjava3.core.Flowable
import ru.jsavings.data.repository.database.transaction.TransactionRepository
import ru.jsavings.domain.mappers.database.TransactionMapper
import ru.jsavings.domain.model.database.category.TransactionCategoryType
import ru.jsavings.domain.model.database.transaction.BaseTransactionData
import ru.jsavings.domain.model.database.transaction.TemporalTransactions
import ru.jsavings.domain.model.database.transaction.Transaction
import ru.jsavings.domain.usecase.common.BaseUseCase

/**
 * Get all transactions from database by account id to which they belongs
 * @param repository [TransactionRepository] to interact with database
 * @param transactionMapper To map entity to model
 *
 * @author JustSpace
 */
class GetAllTransactionsByAccountIdUseCase(
	private val repository: TransactionRepository,
	private val transactionMapper: TransactionMapper,
) : BaseUseCase() {

	/**
	 * Invokes usecase
	 * @param accountId Id of account
	 * @return [Flowable] source with transactions grouped by day when they were made
	 *
	 * @author JustSpace
	 */
	operator fun invoke(accountId: Long): Flowable<List<TemporalTransactions>> = repository
		.getAllTransactionsByAccountId(accountId)
		.map { it.map { transactionGroupEntity -> transactionMapper.mapEntityToModel(transactionGroupEntity) } }
		.map {
			it.groupByTo(LinkedHashMap()) { transaction -> transaction.dateDay }.map { entry ->
				TemporalTransactions(entry.key, entry.value)
			}
		}
}
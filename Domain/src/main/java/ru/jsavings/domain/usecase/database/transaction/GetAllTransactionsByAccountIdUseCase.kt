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
) : BaseUseCase {

	private fun MutableList<Transaction>.getSumOf(type: TransactionCategoryType): Double = sumOf {
		if (it.category != null && it.category.categoryType == type) it.sumInAccountCurrency else 0.0
	}

	/**
	 * Invokes usecase
	 * @param accountId Id of account
	 * @return [Flowable] source with transactions grouped by day when they were made
	 *
	 * @author JustSpace
	 */
	@SuppressLint("SimpleDateFormat")
	operator fun invoke(accountId: Long): Flowable<List<BaseTransactionData>> = repository
		.getAllTransactionsByAccountId(accountId)
		.map { it.map { transactionEntity -> transactionMapper.mapEntityToModel(transactionEntity) } }
		.map {
			val newList = mutableListOf<BaseTransactionData>()
			it.groupByTo(LinkedHashMap()) { transaction -> transaction.dateDay }.forEach { entry ->
				newList.add(TemporalTransactions(
					dayOfTransactions = entry.key,
					totalIncome = entry.value.getSumOf(TransactionCategoryType.INCOME),
					totalConsumption = entry.value.getSumOf(TransactionCategoryType.CONSUMPTION)
				))
				newList.addAll(entry.value.sortedByDescending { it.dateTime })
			}
			newList
		}
}
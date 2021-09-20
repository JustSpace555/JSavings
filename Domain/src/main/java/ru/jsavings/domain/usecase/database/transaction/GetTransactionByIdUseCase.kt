package ru.jsavings.domain.usecase.database.transaction

import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.repository.database.transaction.TransactionRepository
import ru.jsavings.domain.mappers.database.TransactionMapper
import ru.jsavings.domain.model.database.transaction.Transaction
import ru.jsavings.domain.usecase.common.BaseUseCase

/**
 * Usecase for getting transaction by id from database
 * @param transactionRepository [TransactionRepository] to get data from
 * @param transactionMapper [TransactionMapper] to map entity to model
 *
 * @author JustSpace
 */
class GetTransactionByIdUseCase(
	private val transactionRepository: TransactionRepository,
	private val transactionMapper: TransactionMapper
) : BaseUseCase() {

	/**
	 * Invokes usecase
	 * @param transactionId Id of transaction to get from database
	 * @return [Single] source with [TransactionGroup] data
	 *
	 * @author JustSpace
	 */
	operator fun invoke(transactionId: Long): Single<Transaction> =
		transactionRepository.getTransactionById(transactionId).map { transactionGroupEntity ->
			transactionMapper.mapEntityToModel(transactionGroupEntity)
		}
}
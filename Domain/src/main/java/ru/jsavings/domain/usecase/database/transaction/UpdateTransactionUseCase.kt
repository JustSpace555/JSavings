package ru.jsavings.domain.usecase.database.transaction

import io.reactivex.rxjava3.core.Completable
import ru.jsavings.data.entity.database.AccountEntity
import ru.jsavings.data.entity.database.TransactionGroupEntity
import ru.jsavings.data.repository.database.transaction.TransactionRepository
import ru.jsavings.domain.mappers.database.TransactionMapper
import ru.jsavings.domain.model.database.transaction.Transaction
import ru.jsavings.domain.usecase.common.BaseUseCase

/**
 * Update transaction info in database usecase
 * @param transactionRepository [TransactionRepository] to interact with database
 * @param transactionMapper [TransactionMapper]
 *
 * @author JustSpace
 */
class UpdateTransactionUseCase(
	private val transactionRepository: TransactionRepository,
	private val transactionMapper: TransactionMapper
) : BaseUseCase {

	private fun getUpdateCompletable(
		account: AccountEntity,
		oldTransaction: TransactionGroupEntity,
		newTransaction: Transaction
	): Completable {
		
	}

	/**
	 * Invokes usecase
	 * @param transaction Transaction with new data to update
	 * @return [Completable] source of action
	 *
	 * @author JustSpace
	 */
	operator fun invoke(transaction: Transaction): Completable = transactionRepository.updateTransaction(
		transactionMapper.mapModelToEntity(transaction)
	)
}
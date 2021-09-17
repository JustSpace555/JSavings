package ru.jsavings.domain.usecase.database.transaction

import io.reactivex.rxjava3.core.Completable
import org.koin.core.parameter.emptyParametersHolder
import ru.jsavings.data.entity.database.AccountEntity
import ru.jsavings.data.entity.database.TransactionGroupEntity
import ru.jsavings.data.entity.database.WalletEntity
import ru.jsavings.data.repository.database.account.AccountRepository
import ru.jsavings.data.repository.database.transaction.TransactionRepository
import ru.jsavings.data.repository.database.wallet.WalletRepository
import ru.jsavings.domain.mappers.database.TransactionMapper
import ru.jsavings.domain.model.database.category.TransactionCategoryType
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
package ru.jsavings.domain.usecase.database.transaction

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.entity.database.TransactionEntity
import ru.jsavings.data.entity.database.WalletEntity
import ru.jsavings.data.repository.database.transaction.TransactionRepository
import ru.jsavings.data.repository.database.transaction.caterory.TransactionCategoryRepository
import ru.jsavings.data.repository.database.wallet.WalletRepository
import ru.jsavings.domain.mappers.database.TransactionMapper
import ru.jsavings.domain.mappers.database.WalletMapper
import ru.jsavings.domain.model.database.transaction.Transaction
import ru.jsavings.domain.model.database.transaction.category.TransactionCategory
import ru.jsavings.domain.model.database.wallet.Wallet
import ru.jsavings.domain.usecase.common.BaseUseCase
import java.util.*

/**
 * Get transactions from database by account id
 * @param transactionCategoryRepository [TransactionRepository] To interact with transactions' table
 * @param walletRepository [WalletRepository] To interact with wallets' table
 * @param transactionRepository [TransactionCategoryRepository] To interact with transaction categories' table
 * @param transactionMapper [TransactionMapper] To map [TransactionEntity] to [Transaction]
 *
 * @author JustSpace
 */
class GetTransactionsByAccountIdUseCase(
	private val transactionRepository: TransactionRepository,
	private val walletRepository: WalletRepository,
	private val transactionCategoryRepository: TransactionCategoryRepository,
	private val transactionMapper: TransactionMapper
) : BaseUseCase {

	/**
	 * Invokes usecase
	 * @param accountId Id of account
	 * @return [Single] source of list with [Transaction]
	 *
	 * @author JustSpace
	 */
	operator fun invoke(accountId: Long, timePeriod: Pair<Date, Date>): Single<List<Transaction>> {

		lateinit var transactionEntity: TransactionEntity
		lateinit var walletEntity: WalletEntity

		return transactionRepository
			.getTransactionsByAccountIdAndTime(
				accountId,
				timePeriod.first.time,
				timePeriod.second.time
			)
			.flattenAsObservable { it }
			.flatMapSingle {
				transactionEntity = it
				walletRepository.getWalletById(it.walletFkId)
			}.flatMapSingle {
				transactionCategoryRepository.getCategoryById(transactionEntity.categoryFkId)
			}.map { categoryEntity ->
				transactionMapper.mapEntityToModel(transactionEntity, walletEntity, categoryEntity)
			}.toList()
	}
}
package ru.jsavings.domain.usecase.database.transaction

import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.repository.database.transaction.TransactionRepository
import ru.jsavings.domain.usecase.common.BaseUseCase
import java.util.*

/**
 * Use case for getting time of last transaction in account
 * @param transactionRepository [TransactionRepository] to get transaction info from
 *
 * @author Михаил Мошков
 */
class GetLastTransactionTimeUseCase(
	private val transactionRepository: TransactionRepository
) : BaseUseCase {

	companion object {
		const val EMPTY_TIME = -1L
	}

	/**
	 * Invoke usecase
	 * @param accountId Id of account to get transaction info from
	 * @return [Single] source with
	 * @author Михаил Мошков
	 */
	operator fun invoke(accountId: Long): Single<Date> =
		transactionRepository.getLastTransactionDateByAccountId(accountId)
			.defaultIfEmpty(EMPTY_TIME)
			.map { dateLong ->
				Date(if (dateLong == EMPTY_TIME) 0 else dateLong)
			}

}
package ru.jsavings.domain.interactor.database

import org.koin.java.KoinJavaComponent.inject
import ru.jsavings.domain.interactor.common.BaseInteractor
import ru.jsavings.domain.usecase.database.transaction.GetLastTransactionTimeUseCase
import ru.jsavings.domain.usecase.database.transaction.GetTransactionsByAccountIdUseCase

/**
 * Interactor for all transaction usecases
 *
 * @author JustSpace
 */
class TransactionInteractor : BaseInteractor {

	/**
	 * @see GetTransactionsByAccountIdUseCase
	 *
	 * @author JustSpace
	 */
	val getTransactionsByAccountIdUseCase by inject(GetTransactionsByAccountIdUseCase::class.java)

	/**
	 * @see GetLastTransactionTimeUseCase
	 *
	 * @author Михаил Мошков
	 */
	val getLastTransactionTimeUseCase by inject(GetLastTransactionTimeUseCase::class.java)
}
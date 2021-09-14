package ru.jsavings.domain.interactor.database

import org.koin.java.KoinJavaComponent.inject
import ru.jsavings.domain.interactor.common.BaseInteractor
import ru.jsavings.domain.usecase.database.transaction.GetAllTransactionsByAccountIdUseCase
import ru.jsavings.domain.usecase.database.transaction.GetTransactionByIdUseCase
import ru.jsavings.domain.usecase.database.transaction.SaveNewTransactionUseCase

/**
 * Interactor for all transaction usecases
 *
 * @author JustSpace
 */
class TransactionInteractor : BaseInteractor {

	/**
	 * @see SaveNewTransactionUseCase
	 *
	 * @author JustSpace
	 */
	val saveNewTransactionUseCase by inject(SaveNewTransactionUseCase::class.java)

	/**
	 * @see GetAllTransactionsByAccountIdUseCase
	 *
	 * @author JustSpace
	 */
	val getAllTransactionsByAccountIdUseCase by inject(GetAllTransactionsByAccountIdUseCase::class.java)

	/**
	 * @see GetTransactionByIdUseCase
	 *
	 * @author JustSpace
	 */
	val getTransactionByIdUseCase by inject(GetTransactionByIdUseCase::class.java)
}
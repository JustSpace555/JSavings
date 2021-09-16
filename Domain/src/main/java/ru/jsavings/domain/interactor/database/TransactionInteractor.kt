package ru.jsavings.domain.interactor.database

import org.koin.java.KoinJavaComponent.inject
import ru.jsavings.domain.interactor.common.BaseInteractor
import ru.jsavings.domain.usecase.database.transaction.*

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

	/**
	 * @see DeleteTransactionByIdUseCase
	 *
	 * @author JustSpace
	 */
	val deleteTransactionByIdUseCase by inject(DeleteTransactionByIdUseCase::class.java)

	/**
	 * @see UpdateTransactionUseCase
	 *
	 * @author JustSpace
	 */
	val updateTransactionUseCase by inject(UpdateTransactionUseCase::class.java)
}
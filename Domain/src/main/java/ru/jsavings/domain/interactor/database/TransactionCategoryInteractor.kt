package ru.jsavings.domain.interactor.database

import org.koin.java.KoinJavaComponent.inject
import ru.jsavings.domain.interactor.common.BaseInteractor
import ru.jsavings.domain.usecase.database.category.*

/**
 * Interactor for transaction categories table
 *
 * @author Михаил Мошков
 */
class TransactionCategoryInteractor : BaseInteractor {

	/**
	 * @see GetTransactionCategoryByIdUseCase
	 *
	 * @author JustSpace
	 */
	val getTransactionCategoryByIdUseCase by inject(GetTransactionCategoryByIdUseCase::class.java)

	/**
	 * @see GetAllCategoriesByAccountIdUseCase
	 *
	 * @author Михаил Мошков
	 */
	val getAllCategoriesByAccountIdUseCase by inject(GetAllCategoriesByAccountIdUseCase::class.java)

	/**
	 * @see InsertNewCategoryUseCase
	 *
	 * @author Михаил Мошков
	 */
	val insertNewCategoryUseCase by inject(InsertNewCategoryUseCase::class.java)

	/**
	 * @see DeleteTransactionCategoryUseCase
	 *
	 * @author JustSpace
	 */
	val deleteTransactionCategoryUseCase by inject(DeleteTransactionCategoryUseCase::class.java)

	/**
	 * @see UpdateTransactionCategoryUseCase
	 *
	 * @author JustSpace
	 */
	val updateTransactionCategoryUseCase by inject(UpdateTransactionCategoryUseCase::class.java)
}
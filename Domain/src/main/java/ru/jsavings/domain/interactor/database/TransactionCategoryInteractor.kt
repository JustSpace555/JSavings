package ru.jsavings.domain.interactor.database

import org.koin.java.KoinJavaComponent.inject
import ru.jsavings.domain.interactor.common.BaseInteractor
import ru.jsavings.domain.usecase.database.transaction.category.GetAllCategoriesByAccountIdUseCase
import ru.jsavings.domain.usecase.database.transaction.category.InsertNewCategoryUseCase

/**
 * Interactor for transaction categories table
 *
 * @author Михаил Мошков
 */
class TransactionCategoryInteractor : BaseInteractor {

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
}
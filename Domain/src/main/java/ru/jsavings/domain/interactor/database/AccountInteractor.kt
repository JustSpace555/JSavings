package ru.jsavings.domain.interactor.database

import org.koin.java.KoinJavaComponent.inject
import ru.jsavings.domain.interactor.common.BaseInteractor
import ru.jsavings.domain.usecase.database.account.GetAccountByIdUseCase
import ru.jsavings.domain.usecase.database.account.GetAllAccountsUseCase
import ru.jsavings.domain.usecase.database.account.InsertAccountUseCase

/**
 * Interactor for all account usecases
 *
 * @author JustSpace
 */
class AccountInteractor : BaseInteractor {

	/**
	 * @see GetAllAccountsUseCase
	 *
	 * @author JustSpace
	 */
	val getAllAccountsUseCase by inject(GetAllAccountsUseCase::class.java)

	/**
	 * @see InsertAccountUseCase
	 *
	 * @author JustSpace
	 */
	val insertAccountUseCase by inject(InsertAccountUseCase::class.java)

	/**
	 * @see GetAccountByIdUseCase
	 *
	 * @author Михаил Мошков
	 */
	val getAccountByIdUseCase by inject(GetAccountByIdUseCase::class.java)
}
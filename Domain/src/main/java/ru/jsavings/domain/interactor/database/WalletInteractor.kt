package ru.jsavings.domain.interactor.database

import org.koin.java.KoinJavaComponent.inject
import ru.jsavings.domain.interactor.common.BaseInteractor
import ru.jsavings.domain.usecase.database.wallet.GetWalletsByAccountIdUseCase
import ru.jsavings.domain.usecase.database.wallet.InsertNewWalletUseCase

/**
 * Interactor for all wallet's usecases
 *
 * @author JustSpace
 */
class WalletInteractor : BaseInteractor {

	/**
	 * @see InsertNewWalletUseCase
	 *
	 * @author JustSpace
	 */
	val insertNewWalletUseCase by inject(InsertNewWalletUseCase::class.java)

	/**
	 * @see GetWalletsByAccountIdUseCase
	 *
	 * @author Михаил Мошков
	 */
	val getWalletsByAccountIdUseCase by inject(GetWalletsByAccountIdUseCase::class.java)
}
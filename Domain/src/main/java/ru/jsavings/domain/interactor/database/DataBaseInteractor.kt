package ru.jsavings.domain.interactor.database

import org.koin.java.KoinJavaComponent.inject
import ru.jsavings.domain.interactor.common.BaseInteractor

/**
 * Interactor of all database's usecases
 *
 * @author JustSpace
 */
class DataBaseInteractor : BaseInteractor {

	/**
	 * @see AccountInteractor
	 *
	 * @author JustSpace
	 */
	val accountInteractor by inject(AccountInteractor::class.java)

	/**
	 * @see WalletInteractor
	 *
	 * @author JustSpace
	 */
	val walletInteractor by inject(WalletInteractor::class.java)

	/**
	 * @see TransactionInteractor
	 *
	 * @author JustSpace
	 */
	val transactionInteractor by inject(TransactionInteractor::class.java)
}
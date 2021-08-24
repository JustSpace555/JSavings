package ru.jsavings.domain.interactor.network

import org.koin.java.KoinJavaComponent.inject
import ru.jsavings.domain.interactor.common.BaseInteractor
import ru.jsavings.domain.usecase.network.ConvertCurrencyUseCase
import ru.jsavings.domain.usecase.network.GetCryptoCoinsUseCase
import ru.jsavings.domain.usecase.network.GetCurrenciesUseCase

/**
 * Interactor for all network usecases
 *
 * @author JustSpace
 */
class NetworkInteractor : BaseInteractor {

	/**
	 * @see GetCurrenciesUseCase
	 *
	 * @author JustSpace
	 */
	val getCurrenciesUseCase by inject(GetCurrenciesUseCase::class.java)

	/**
	 * @see GetCryptoCoinsUseCase
	 *
	 * @author JustSpace
	 */
	val getCryptoCoinsUseCase by inject(GetCryptoCoinsUseCase::class.java)

	/**
	 * @see ConvertCurrencyUseCase
	 *
	 * @author JustSpace
	 */
	val convertCurrencyUseCase by inject(ConvertCurrencyUseCase::class.java)
}
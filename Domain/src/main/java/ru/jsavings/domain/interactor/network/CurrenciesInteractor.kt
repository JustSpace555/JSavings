package ru.jsavings.domain.interactor.network

import org.koin.java.KoinJavaComponent.inject
import ru.jsavings.domain.interactor.common.BaseInteractor
import ru.jsavings.domain.usecase.network.ConvertCurrencyUseCase
import ru.jsavings.domain.usecase.network.GetCurrenciesUseCase

class CurrenciesInteractor : BaseInteractor {

	val getCurrenciesUseCase by inject(GetCurrenciesUseCase::class.java)

	val convertCurrencyUseCase by inject(ConvertCurrencyUseCase::class.java)
}
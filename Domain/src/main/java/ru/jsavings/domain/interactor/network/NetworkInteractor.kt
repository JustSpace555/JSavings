package ru.jsavings.domain.interactor.network

import org.koin.java.KoinJavaComponent.inject
import ru.jsavings.domain.interactor.common.BaseInteractor

class NetworkInteractor : BaseInteractor {

	val cryptoCoinInteractor by inject(CryptoCoinInteractor::class.java)

	val currenciesInteractor by inject(CurrenciesInteractor::class.java)
}
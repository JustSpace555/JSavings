package ru.jsavings.domain.interactor.network

import org.koin.java.KoinJavaComponent
import ru.jsavings.domain.interactor.common.BaseInteractor
import ru.jsavings.domain.usecase.network.GetCryptoCoinsUseCase

class CryptoCoinInteractor : BaseInteractor {

	val getCryptoCoinsUseCase by KoinJavaComponent.inject(GetCryptoCoinsUseCase::class.java)
}
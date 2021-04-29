package ru.jsavings.domain.usecase.network

import io.reactivex.rxjava3.core.Observable
import ru.jsavings.data.model.network.crypto.CryptoCoin
import ru.jsavings.data.repository.network.crypto.CryptoRepository
import ru.jsavings.domain.usecase.common.ObservableUseCase

class GetAllCoinsUseCase(
	private val cryptoRepository: CryptoRepository
) : ObservableUseCase<List<CryptoCoin>, Unit>() {

	override fun buildObservableUseCase(params: Unit): Observable<List<CryptoCoin>> =
		cryptoRepository.getAvailableCoinsList()
}
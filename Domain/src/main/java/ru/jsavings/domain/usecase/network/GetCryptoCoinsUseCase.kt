package ru.jsavings.domain.usecase.network

import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.model.network.crypto.CryptoCoin
import ru.jsavings.data.repository.network.crypto.CryptoRepository
import ru.jsavings.domain.usecase.common.SingleUseCase

class GetCryptoCoinsUseCase(
	override val repository: CryptoRepository
) : SingleUseCase<List<CryptoCoin>, Unit>() {

	override fun buildSingleUseCase(params: Unit): Single<List<CryptoCoin>> =
		repository.getAvailableCryptoCoinsList()
}
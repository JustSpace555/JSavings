package ru.jsavings.domain.usecase.network

import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.repository.network.crypto.CryptoRepository
import ru.jsavings.domain.usecase.common.SingleUseCase

class GetCoinsPriceUseCase(private val cryptoRepository: CryptoRepository) :
	SingleUseCase<Map<String, Map<String, Int?>>, Pair<List<String>, List<String>>>() {

	override fun buildSingleUseCase(
		params: Pair<List<String>, List<String>>
	): Single<Map<String, Map<String, Int?>>> = cryptoRepository.getCoinPrice(
		params.first.joinToString(separator = ","),
		params.second.joinToString(separator = ",")
	)
}
package ru.jsavings.domain.usecase.network

import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.model.network.ConversionInfo
import ru.jsavings.data.repository.network.currency.CurrencyRepository
import ru.jsavings.domain.usecase.common.SingleUseCase

class ConvertCurrencyUseCase(
	override val repository: CurrencyRepository
) : SingleUseCase<ConversionInfo, ConversionRequestData>() {

	override fun buildSingleUseCase(params: ConversionRequestData): Single<ConversionInfo> =
		repository.getConversion(params.from, params.to, params.amount, params.precision)
}

data class ConversionRequestData(
	val from: String,
	val to: String,
	val amount: Double,
	val precision: Int = 2
)
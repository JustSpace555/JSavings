package ru.jsavings.domain.usecase.network

import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.repository.network.currency.CurrencyRepository
import ru.jsavings.domain.mappers.network.ConversionMapper
import ru.jsavings.domain.model.network.ConversionInfo
import ru.jsavings.domain.usecase.common.BaseUseCase

/**
 * Usecase to request conversion from one currency or crypto coin to another
 * @param repository [CurrencyRepository] to interact with api
 * @param conversionMapper [ConversionMapper] to map result of request to [ConversionInfo]
 *
 * @author JustSpace
 */
class ConvertCurrencyUseCase(
	private val repository: CurrencyRepository,
	private val conversionMapper: ConversionMapper
) : BaseUseCase() {

	/**
	 * Execute usecase
	 * @param from Currency code or crypto coin id to convert from
	 * @param to Currency code ot crypto coin id to convert to
	 * @param amount Amount of money in currency or crypto coin code / id = [from]
	 * @param precision Precision of conversion
	 * @return [Single] source of [ConversionInfo]
	 *
	 * @author JustSpace
	 */
	operator fun invoke(from: String, to: String, amount: Double, precision: Int = 2): Single<ConversionInfo> =
		repository.getConversion(from, to, amount, precision).map { conversionMapper.mapEntityToModel(it) }
}
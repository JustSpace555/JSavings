package ru.jsavings.data.repository.network.currency

import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.entity.network.ConversionEntity
import ru.jsavings.data.entity.network.CurrencyEntity
import ru.jsavings.data.repository.network.common.BaseNetworkRepository

/**
 * Repository for currency from ExchangeRate api
 * @author JustSpace
 */
interface CurrencyRepository : BaseNetworkRepository {

	fun getAvailableCurrencies(): Single<CurrencyEntity>

	fun getConversion(
		from: String,
		to: String,
		amount: Double,
		precision: Int,
	): Single<ConversionEntity>
}
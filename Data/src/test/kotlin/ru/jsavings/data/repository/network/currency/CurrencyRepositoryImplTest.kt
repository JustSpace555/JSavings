package ru.jsavings.data.repository.network.currency

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import ru.jsavings.data.entity.network.ConversionEntity
import ru.jsavings.data.entity.network.CurrencyEntity
import ru.jsavings.data.entity.network.QueryInfo
import ru.jsavings.data.entity.network.RateInfo
import ru.jsavings.data.network.api.exchangerate.ExchangeRateApi

class CurrencyRepositoryImplTest {

	companion object {
		private const val SOME_FROM = "some from"
		private const val SOME_TO = "some to"
		private const val SOME_AMOUNT = 0.0
		private const val SOME_PRECISION = 0
	}

	private lateinit var api: ExchangeRateApi
	private lateinit var currencyRepository: CurrencyRepository

	private val someCurrency = CurrencyEntity(
		success = true,
		symbols = mapOf()
	)

	private val someConversion = ConversionEntity(
		info = RateInfo(0.0),
		query = QueryInfo(SOME_FROM, SOME_TO, SOME_AMOUNT),
		result = 0.0,
		success = true
	)

	@Before
	fun setUp() {
		api = mockk()
		currencyRepository = CurrencyRepositoryImpl(api)
	}

	@Test
	fun getAvailableCurrencies() {

		//Act
		every { api.getCurrencies() } returns Single.just(someCurrency)
		currencyRepository.getAvailableCurrencies()

		//Assert
		verify { api.getCurrencies() }
	}

	@Test
	fun getConversion() {

		//Act
		every { api.getConversion(SOME_FROM, SOME_TO, SOME_AMOUNT, SOME_PRECISION) } returns Single.just(someConversion)
		currencyRepository.getConversion(SOME_FROM, SOME_TO, SOME_AMOUNT, SOME_PRECISION)

		//Assert
		verify { api.getConversion(SOME_FROM, SOME_TO, SOME_AMOUNT, SOME_PRECISION) }
	}
}
package ru.jsavings.domain.usecase.network

import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import ru.jsavings.data.entity.network.CurrencyEntity
import ru.jsavings.data.entity.network.CurrencyInfoEntity
import ru.jsavings.data.repository.network.currency.CurrencyRepository
import ru.jsavings.domain.model.network.currency.Currency
import java.util.*
import java.util.Currency as StandardCurrency

class GetCurrenciesUseCaseTest {

	companion object {
		private const val FIRST_CODE = "USD"
		private const val SECOND_CODE = "EUR"

		private const val SOME_STRING = ""
	}

	private lateinit var repository: CurrencyRepository
	private lateinit var useCase: GetCurrenciesUseCase

	private val someCurrency = CurrencyInfoEntity(
		code = SOME_STRING,
		description = SOME_STRING
	)

	private val firstStandardCurrency = StandardCurrency.getInstance(FIRST_CODE)
	private val firstCurrency = Currency(
		code = firstStandardCurrency.currencyCode,
		name = firstStandardCurrency.displayName,
		symbol = firstStandardCurrency.symbol
	)

	private val secondStandardCurrency = StandardCurrency.getInstance(SECOND_CODE)
	private val secondCurrency = Currency(
		code = secondStandardCurrency.currencyCode,
		name = secondStandardCurrency.displayName,
		symbol = secondStandardCurrency.symbol
	)

	private val currenciesEntity = CurrencyEntity(
		success = true,
		symbols = mapOf(FIRST_CODE to someCurrency, SECOND_CODE to someCurrency)
	)

	@Before
	fun setUp() {
		repository = mockk()
		useCase = GetCurrenciesUseCase(repository)

		every { repository.getAvailableCurrencies() } returns Single.just(currenciesEntity)
	}

	@Test
	operator fun invoke() {

		//Arrange
		val expectedResult = listOf(secondCurrency, firstCurrency)

		//Act
		val actualResult = useCase().blockingGet()

		//Assert
		verify { repository.getAvailableCurrencies() }
		Truth.assertThat(actualResult).isEqualTo(expectedResult)
	}
}
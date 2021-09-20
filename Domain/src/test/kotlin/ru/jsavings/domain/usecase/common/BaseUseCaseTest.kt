package ru.jsavings.domain.usecase.common

import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import ru.jsavings.data.entity.network.ConversionEntity
import ru.jsavings.data.entity.network.QueryInfo
import ru.jsavings.data.entity.network.RateInfo
import ru.jsavings.data.repository.network.currency.CurrencyRepository

class BaseUseCaseTest : BaseUseCase() {

	companion object {
		private const val FROM = "FROM"
		private const val TO = "TO"
		private const val AMOUNT = 10.0
		private const val RESULT = 20.0
		private const val PRECISION = 2
	}

	private val conversionEntity = ConversionEntity(
		info = RateInfo(0.0),
		query = QueryInfo(FROM, TO, AMOUNT),
		result = RESULT,
		success = true
	)

	private lateinit var currencyRepository: CurrencyRepository

	@Before
	fun setUp() {
		currencyRepository = mockk()
	}

	@Test
	fun getConversionRequestNetwork() {

		//Act
		every { currencyRepository.getConversion(FROM, TO, AMOUNT, PRECISION) } returns Single.just(conversionEntity)
		getConversion(currencyRepository, FROM, TO, AMOUNT, PRECISION).blockingGet()

		//Assert
		verify { currencyRepository.getConversion(FROM, TO, AMOUNT, PRECISION) }
	}

	@Test
	fun getConversionFromNetwork() {

		//Arrange
		val expectedResult = RESULT

		//Act
		every { currencyRepository.getConversion(FROM, TO, AMOUNT, PRECISION) } returns Single.just(conversionEntity)
		val actualResult = getConversion(currencyRepository, FROM, TO, AMOUNT, PRECISION).blockingGet()

		//Assert
		Truth.assertThat(actualResult).isEqualTo(expectedResult)
	}

	@Test
	fun getConversionNonNetwork() {

		//Arrange
		val expectedResult = AMOUNT

		//Act
		val actualResult = getConversion(currencyRepository, FROM, FROM, AMOUNT, PRECISION).blockingGet()

		//Assert
		Truth.assertThat(actualResult).isEqualTo(expectedResult)
	}
}
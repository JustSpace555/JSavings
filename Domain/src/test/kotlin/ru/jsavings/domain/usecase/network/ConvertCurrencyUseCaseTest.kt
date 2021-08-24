package ru.jsavings.domain.usecase.network

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
import ru.jsavings.domain.mappers.network.ConversionMapper
import ru.jsavings.domain.model.network.ConversionInfo

class ConvertCurrencyUseCaseTest {

	companion object {
		private const val FROM = "from"
		private const val TO = "to"
		private const val AMOUNT = 25.0
		private const val RESULT = 5.0
	}

	private val someConversionEntity = ConversionEntity(
		info = RateInfo(0.0),
		query = QueryInfo(FROM, TO, AMOUNT),
		result = RESULT,
		success = true
	)

	private val someConversionModel = ConversionInfo(
		amount = AMOUNT,
		from = FROM,
		to = TO,
		result = RESULT
	)

	private lateinit var repository: CurrencyRepository
	private lateinit var mapper: ConversionMapper
	private lateinit var useCase: ConvertCurrencyUseCase

	@Before
	fun setUp() {
		repository = mockk()
		mapper = mockk()
		useCase = ConvertCurrencyUseCase(repository, mapper)
	}

	@Test
	operator fun invoke() {

		//Act
		every { repository.getConversion(FROM, TO, AMOUNT, 2) } returns Single.just(someConversionEntity)
		every { mapper.mapEntityToModel(someConversionEntity) } returns someConversionModel
		useCase(FROM, TO, AMOUNT).blockingGet()

		//Assert
		verify { repository.getConversion(FROM, TO, AMOUNT, 2) }
	}
}
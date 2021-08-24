package ru.jsavings.domain.mappers.network

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import ru.jsavings.data.entity.network.ConversionEntity
import ru.jsavings.data.entity.network.QueryInfo
import ru.jsavings.data.entity.network.RateInfo
import ru.jsavings.domain.model.network.ConversionInfo

class ConversionMapperTest {

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

	private lateinit var mapper: ConversionMapper

	@Before
	fun setUp() {
		mapper = ConversionMapper()
	}

	@Test
	fun mapEntityToModel() {

		//Arrange
		val expectedResult = someConversionModel

		//Act
		val actualResult = mapper.mapEntityToModel(someConversionEntity)

		//Arrange
		Truth.assertThat(actualResult).isEqualTo(expectedResult)
	}
}
package ru.jsavings.domain.mappers.network

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import ru.jsavings.data.entity.network.CryptoCoinEntity
import ru.jsavings.data.entity.network.CryptoCoinInfoEntity
import ru.jsavings.domain.common.BaseUnitTest
import ru.jsavings.domain.model.network.crypto.CryptoCoin

class CryptoCoinsMapperTest : BaseUnitTest() {

	private lateinit var mapper: CryptoCoinsMapper

	@Before
	fun setUp() {
		mapper = CryptoCoinsMapper()
	}

	@Test
	fun mapEntityToModel() {

		//Arrange
		val expectedResult = listOf(cryptoCoinModel)

		//Act
		val actualResult = mapper.mapEntityToModel(cryptoCoinEntity)

		//Assert
		Truth.assertThat(actualResult).isEqualTo(expectedResult)
	}
}
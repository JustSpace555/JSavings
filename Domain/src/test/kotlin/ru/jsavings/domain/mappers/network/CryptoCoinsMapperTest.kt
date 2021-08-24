package ru.jsavings.domain.mappers.network

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import ru.jsavings.data.entity.network.CryptoCoinEntity
import ru.jsavings.data.entity.network.CryptoCoinInfoEntity
import ru.jsavings.domain.model.network.crypto.CryptoCoin

class CryptoCoinsMapperTest {

	companion object {
		private const val ID = "some id"
		private const val NAME = "some name"
		private const val SYMBOL = "some symbol"
	}

	private val cryptoCoinInfoEntity = CryptoCoinInfoEntity(
		name = NAME,
		symbol = SYMBOL
	)

	private val someCryptoCoinEntity = CryptoCoinEntity(
		success = true,
		cryptocurrencies = mapOf(ID to cryptoCoinInfoEntity)
	)

	private val someCryptoCoinModel = CryptoCoin(
		id = ID,
		name = NAME,
		symbol = SYMBOL
	)

	private lateinit var mapper: CryptoCoinsMapper

	@Before
	fun setUp() {
		mapper = CryptoCoinsMapper()
	}

	@Test
	fun mapEntityToModel() {

		//Arrange
		val expectedResult = listOf(someCryptoCoinModel)

		//Act
		val actualResult = mapper.mapEntityToModel(someCryptoCoinEntity)

		//Assert
		Truth.assertThat(actualResult).isEqualTo(expectedResult)
	}
}
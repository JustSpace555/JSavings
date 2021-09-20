package ru.jsavings.domain.mappers.database

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import ru.jsavings.data.entity.database.WalletEntity
import ru.jsavings.domain.common.BaseUnitTest
import ru.jsavings.domain.model.database.wallet.Wallet
import ru.jsavings.domain.model.database.wallet.WalletType

class WalletMapperTest : BaseUnitTest() {

	private lateinit var mapper: WalletMapper

	@Before
	fun setUp() {
		mapper = WalletMapper()
	}

	@Test
	fun mapEntityToModel() {

		//Arrange
		val expectedResult = walletModel

		//Act
		val actualResult = mapper.mapEntityToModel(walletEntity)

		//Assert
		Truth.assertThat(actualResult).isEqualTo(expectedResult)
	}

	@Test
	fun mapModelToEntity() {

		//Arrange
		val expectedResult = walletEntity

		//Act
		val actualResult = mapper.mapModelToEntity(walletModel)

		//Assert
		Truth.assertThat(actualResult).isEqualTo(expectedResult)
	}
}
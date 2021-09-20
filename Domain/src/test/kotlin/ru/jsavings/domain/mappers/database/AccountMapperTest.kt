package ru.jsavings.domain.mappers.database

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import ru.jsavings.data.entity.database.AccountEntity
import ru.jsavings.domain.common.BaseUnitTest
import ru.jsavings.domain.model.database.Account

class AccountMapperTest : BaseUnitTest() {

	private lateinit var mapper: AccountMapper

	@Before
	fun setUp() {
		mapper = AccountMapper()
	}

	@Test
	fun mapEntityToModel() {

		//Arrange
		val expectedResult = accountModel

		//Act
		val actualResult = mapper.mapEntityToModel(accountEntity)

		//Assert
		Truth.assertThat(actualResult).isEqualTo(expectedResult)
	}

	@Test
	fun mapModelToEntity() {

		//Arrange
		val expectedResult = accountEntity

		//Act
		val actualResult = mapper.mapModelToEntity(accountModel)

		//Assert
		Truth.assertThat(actualResult).isEqualTo(expectedResult)
	}
}
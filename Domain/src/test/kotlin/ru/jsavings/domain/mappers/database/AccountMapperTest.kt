package ru.jsavings.domain.mappers.database

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import ru.jsavings.data.entity.database.AccountEntity
import ru.jsavings.domain.model.database.Account

class AccountMapperTest {

	companion object {
		private const val ID = 1L
		private const val NAME = "some name"
		private const val CURRENCY = "some currency"
		private const val BALANCE = 25.0
	}

	private val someAccountEntity = AccountEntity(
		accountId = ID,
		accountName = NAME,
		mainCurrencyCode = CURRENCY,
		balanceInMainCurrency = BALANCE
	)

	private val someAccountModel = Account(
		accountId = ID,
		name = NAME,
		mainCurrencyCode = CURRENCY,
		balanceInMainCurrency = BALANCE
	)

	private lateinit var mapper: AccountMapper

	@Before
	fun setUp() {
		mapper = AccountMapper()
	}

	@Test
	fun mapEntityToModel() {

		//Arrange
		val expectedResult = someAccountModel

		//Act
		val actualResult = mapper.mapEntityToModel(someAccountEntity)

		//Assert
		Truth.assertThat(actualResult).isEqualTo(expectedResult)
	}

	@Test
	fun mapModelToEntity() {

		//Arrange
		val expectedResult = someAccountEntity

		//Act
		val actualResult = mapper.mapModelToEntity(someAccountModel)

		//Assert
		Truth.assertThat(actualResult).isEqualTo(expectedResult)
	}
}
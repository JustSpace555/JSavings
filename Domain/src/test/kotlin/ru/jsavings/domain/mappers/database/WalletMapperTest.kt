package ru.jsavings.domain.mappers.database

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import ru.jsavings.data.entity.database.WalletEntity
import ru.jsavings.domain.model.database.wallet.Wallet
import ru.jsavings.domain.model.database.wallet.WalletCategoryType

class WalletMapperTest {

	companion object {
		private const val ACCOUNT_ID = 1L
		private const val BALANCE = 25.0
		private val CATEGORY = WalletCategoryType.CASH
		private const val COLOR = 0
		private const val CREDIT_LIMIT = 0.0
		private const val GRACE_PERIOD = 0
		private const val CURRENCY_CODE = "USD"
		private const val ICON_PATH = ""
		private const val INTEREST_RATE = 0.0
		private const val PAYMENT_DAY = 0
		private const val NAME = "some name"
		private const val ID = 1L
	}

	private val someWalletModel = Wallet(
		accountId = ACCOUNT_ID,
		balance = BALANCE,
		category = CATEGORY,
		color = COLOR,
		creditLimit = CREDIT_LIMIT,
		gracePeriod = GRACE_PERIOD,
		currency = CURRENCY_CODE,
		iconPath = ICON_PATH,
		interestRate = INTEREST_RATE,
		paymentDay = PAYMENT_DAY,
		name = NAME,
		walletId = ID
	)

	private val someWalletEntity = WalletEntity(
		accountFkId = ACCOUNT_ID,
		balance = BALANCE,
		category = CATEGORY.toString(),
		color = COLOR,
		creditLimit = CREDIT_LIMIT,
		gracePeriod = GRACE_PERIOD,
		currencyCode = CURRENCY_CODE,
		iconPath = ICON_PATH,
		interestRate = INTEREST_RATE,
		paymentDay = PAYMENT_DAY,
		walletName = NAME,
		walletId = ID
	)

	private lateinit var mapper: WalletMapper

	@Before
	fun setUp() {
		mapper = WalletMapper()
	}

	@Test
	fun mapEntityToModel() {

		//Arrange
		val expectedResult = someWalletModel

		//Act
		val actualResult = mapper.mapEntityToModel(someWalletEntity)

		//Assert
		Truth.assertThat(actualResult).isEqualTo(expectedResult)
	}

	@Test
	fun mapModelToEntity() {

		//Arrange
		val expectedResult = someWalletEntity

		//Act
		val actualResult = mapper.mapModelToEntity(someWalletModel)

		//Assert
		Truth.assertThat(actualResult).isEqualTo(expectedResult)
	}
}
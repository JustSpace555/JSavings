package ru.jsavings.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth
import org.junit.After
import org.junit.Before
import org.junit.Test
import ru.jsavings.data.database.JSavingsDataBase
import ru.jsavings.data.database.dao.WalletDao
import ru.jsavings.data.entity.database.AccountEntity
import ru.jsavings.data.entity.database.WalletEntity

class WalletDaoTest {

	private lateinit var db: JSavingsDataBase
	private lateinit var walletDao: WalletDao

	private val someAccount = AccountEntity(
		accountId = 1,
		accountName = "some name",
		mainCurrencyCode = "USD",
		balanceInMainCurrency = 25.0
	)

	private val someWallet = WalletEntity(
		accountFkId = 1,
		balance = 25.0,
		category = "some category",
		color = 0,
		creditLimit = 0.0,
		gracePeriod = 0,
		currencyCode = "USD",
		iconPath = "",
		interestRate = 0.0,
		paymentDay = 0,
		walletName = "some name",
		walletId = 1
	)

	@Before
	fun setUp() {
		val context = ApplicationProvider.getApplicationContext<Context>()
		db = Room.inMemoryDatabaseBuilder(context, JSavingsDataBase::class.java).build()
		walletDao = db.walletDao()

		//Inserting wallet
		db.accountDao().insertNewAccount(someAccount)
			.flatMap { walletDao.insertNewWallet(someWallet.copy(walletId = 0)) }
			.blockingGet()
	}

	@After
	fun closeDb() {
		db.close()
	}

	@Test
	fun getWalletsByAccountId() {

		//Arrange
		val expectedResult = listOf(someWallet)

		//Act
		val actualResult = walletDao.getWalletsByAccountId(someAccount.accountId).blockingGet()

		//Assert
		Truth.assertThat(actualResult).isEqualTo(expectedResult)
	}
}
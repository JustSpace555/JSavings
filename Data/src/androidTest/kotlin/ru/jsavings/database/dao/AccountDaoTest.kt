package ru.jsavings.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth
import org.junit.After
import org.junit.Before
import org.junit.Test
import ru.jsavings.data.database.JSavingsDataBase
import ru.jsavings.data.database.dao.AccountDao
import ru.jsavings.data.entity.database.AccountEntity

class AccountDaoTest {

	private lateinit var db: JSavingsDataBase
	private lateinit var accountDao: AccountDao

	private val someAccount = AccountEntity(
		accountId = 1,
		accountName = "some name",
		mainCurrencyCode = "USD",
		balanceInMainCurrency = 25.0
	)

	@Before
	fun setUp() {
		val context = ApplicationProvider.getApplicationContext<Context>()
		db = Room.inMemoryDatabaseBuilder(context, JSavingsDataBase::class.java).build()
		accountDao = db.accountDao()

		//Inserting new account
		accountDao.insertNewAccount(someAccount.copy(accountId = 0)).blockingGet()
	}

	@After
	fun closeDb() {
		db.close()
	}

	@Test
	fun getAllAccountsTest() {

		//Arrange
		val expectedResult = listOf(someAccount)

		//Act
		val actualResult = accountDao.getAllAccounts().blockingGet()

		//Assert
		Truth.assertThat(actualResult).isEqualTo(expectedResult)
	}

	@Test
	fun getAccountById() {

		//Arrange
		val expectedResult = someAccount

		//Act
		val actualResult = accountDao.getAccountByIdSingle(someAccount.accountId).blockingGet()

		//Assert
		Truth.assertThat(actualResult).isEqualTo(expectedResult)
	}
}
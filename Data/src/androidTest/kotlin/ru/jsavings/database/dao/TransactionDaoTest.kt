package ru.jsavings.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth
import org.junit.After
import org.junit.Before
import org.junit.Test
import ru.jsavings.data.database.JSavingsDataBase
import ru.jsavings.data.database.dao.TransactionDao
import ru.jsavings.data.entity.database.AccountEntity
import ru.jsavings.data.entity.database.TransactionCategoryEntity
import ru.jsavings.data.entity.database.TransactionEntity
import ru.jsavings.data.entity.database.WalletEntity

class TransactionDaoTest {

	private lateinit var db: JSavingsDataBase
	private lateinit var transactionDao: TransactionDao

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

	private val someCategory = TransactionCategoryEntity(
		accountFkId = 1,
		categoryName = "some name",
		color = 0,
		iconPath = "",
		type = "some type",
		categoryId = 1
	)

	private val someTransaction = TransactionEntity(
		categoryFkId = 1,
		date = 0,
		describePicturePath = "",
		description = "",
		sum = 0.0,
		transactionId = 1,
		walletFkId = 1
	)

	@Before
	fun setUp() {
		val context = ApplicationProvider.getApplicationContext<Context>()
		db = Room.inMemoryDatabaseBuilder(context, JSavingsDataBase::class.java).build()
		transactionDao = db.transactionDao()

		//Inserting transaction
		db.accountDao()
			.insertNewAccount(someAccount)
			.flatMap { db.walletDao().insertNewWallet(someWallet) }
			.flatMap { db.categoryDao().insertNewCategory(someCategory) }
			.flatMap { transactionDao.insertNewTransaction(someTransaction.copy(transactionId = 0)) }
			.blockingGet()
	}

	@After
	fun closeDb() {
		db.close()
	}

	@Test
	fun getTransactionByIdTest() {

		//Arrange
		val expectedResult = someTransaction

		//Act
		val actualResult = transactionDao.getTransactionById(someTransaction.transactionId).blockingGet()

		//Assert
		Truth.assertThat(actualResult).isEqualTo(expectedResult)
	}
}
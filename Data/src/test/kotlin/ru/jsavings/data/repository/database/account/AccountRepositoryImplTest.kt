package ru.jsavings.data.repository.database.account

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import ru.jsavings.data.database.dao.AccountDao
import ru.jsavings.data.entity.database.AccountEntity

class AccountRepositoryImplTest {

	private lateinit var accountDao: AccountDao
	private lateinit var accountRepository: AccountRepository

	companion object {
		private const val SOME_ACCOUNT_ID = 1L
		private const val SOME_ACCOUNT_NAME = "some name"
		private const val SOME_ACCOUNT_CURRENCY = "USD"
		private const val SOME_ACCOUNT_BALANCE = 25.0
	}

	private val someAccount = AccountEntity(
		accountId = SOME_ACCOUNT_ID,
		accountName = SOME_ACCOUNT_NAME,
		mainCurrencyCode = SOME_ACCOUNT_CURRENCY,
		balanceInMainCurrency = SOME_ACCOUNT_BALANCE
	)

	@Before
	fun setUp() {
		accountDao = mockk()
		accountRepository = AccountRepositoryImpl(accountDao)
	}

	@Test
	fun insertNewAccount() {

		//Act
		every { accountDao.insertNewAccount(someAccount) } returns Single.just(someAccount.accountId)
		accountRepository.insertNewAccount(someAccount)

		//Assert
		verify { accountDao.insertNewAccount(someAccount) }
	}

	@Test
	fun getAllAccounts() {

		//Act
		every { accountDao.getAllAccounts() } returns Single.just(listOf(someAccount))
		accountRepository.getAllAccounts()

		//Assert
		verify { accountDao.getAllAccounts() }
	}

	@Test
	fun getAccountById() {

		//Act
		every { accountDao.getAccountByIdSingle(someAccount.accountId) } returns Single.just(someAccount)
		accountRepository.getAccountByIdSingle(someAccount.accountId)

		//Assert
		verify { accountDao.getAccountByIdSingle(someAccount.accountId) }
	}

	@Test
	fun insertAccount() {

		//Act
		every { accountDao.insertNewAccount(someAccount) } returns Single.just(someAccount.accountId)
		accountRepository.insertNewAccount(someAccount)

		//Assert
		verify { accountDao.insertNewAccount(someAccount) }
	}

	@Test
	fun updateAccount() {

		//Act
		every { accountDao.updateAccount(someAccount) } answers { Completable.complete() }
		accountRepository.updateAccount(someAccount)

		//Assert
		verify { accountDao.updateAccount(someAccount) }
	}

	@Test
	fun deleteAccount() {

		//Act
		every { accountDao.deleteAccount(someAccount) } returns Completable.complete()
		accountRepository.deleteAccount(someAccount)

		//Assert
		verify { accountDao.deleteAccount(someAccount) }
	}
}
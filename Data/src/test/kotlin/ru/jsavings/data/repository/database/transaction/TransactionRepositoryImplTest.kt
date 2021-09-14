package ru.jsavings.data.repository.database.transaction

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import ru.jsavings.data.database.dao.TransactionDao
import ru.jsavings.data.entity.database.TransactionEntity

class TransactionRepositoryImplTest {

	private lateinit var transactionDao: TransactionDao
	private lateinit var transactionRepository: TransactionRepository

	private val someTransaction = TransactionEntity(
		categoryFkId = 0,
		date = 0,
		describePicturePath = "",
		description = "",
		sumInWalletCurrency = 0.0,
		transactionId = 0,
		fromWalletFkId = 0,
		accountFkId = 0,
		sumInAccountCurrency = 0.0,
		toWalletFkId = null,
		transferSum = null
	)

	@Before
	fun setUp() {
		transactionDao = mockk()
		transactionRepository = TransactionRepositoryImpl(transactionDao)
	}

	@Test
	fun getTransactionById() {

		//Act
		every { transactionDao.getTransactionById(someTransaction.transactionId) } returns Single.just(someTransaction)
		transactionRepository.getTransactionById(someTransaction.transactionId)

		//Assert
		verify { transactionDao.getTransactionById(someTransaction.transactionId) }
	}

	@Test
	fun insertNewTransaction() {

		//Act
		every { transactionDao.insertNewTransaction(someTransaction) } returns Single.just(someTransaction.transactionId)
		transactionRepository.insertNewTransaction(someTransaction)

		//Assert
		verify { transactionDao.insertNewTransaction(someTransaction) }
	}

	@Test
	fun updateTransaction() {

		//Act
		every { transactionDao.updateTransaction(someTransaction) } returns Completable.complete()
		transactionRepository.updateTransaction(someTransaction)

		//Assert
		verify { transactionDao.updateTransaction(someTransaction) }
	}

	@Test
	fun deleteTransaction() {

		//Act
		every { transactionRepository.deleteTransaction(someTransaction) } returns Completable.complete()
		transactionRepository.deleteTransaction(someTransaction)

		//Assert
		verify { transactionDao.deleteTransaction(someTransaction) }
	}
}
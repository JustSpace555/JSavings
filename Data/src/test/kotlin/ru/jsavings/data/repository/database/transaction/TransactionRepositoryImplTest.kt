package ru.jsavings.data.repository.database.transaction

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import ru.jsavings.data.database.dao.TransactionDao
import ru.jsavings.data.entity.database.TransactionCategoryEntity
import ru.jsavings.data.entity.database.TransactionEntity
import ru.jsavings.data.entity.database.TransactionGroupEntity
import ru.jsavings.data.entity.database.WalletEntity

class TransactionRepositoryImplTest {

	private lateinit var transactionDao: TransactionDao
	private lateinit var transactionRepository: TransactionRepository

	companion object {
		private const val CATEGORY_ID = 0L
		private const val DATE_TIME = 0L
		private const val DATE_DAY = 0L
		private const val PICTURE_PATH = ""
		private const val DESCRIPTION = ""
		private const val WALLET_SUM = 0.0
		private const val TRANSACTION_ID = 0L
		private val FROM_WALLET_ID: Long? = null
		private const val ACCOUNT_ID = 0L
		private const val ACCOUNT_SUM = 0.0
		private val TO_WALLET_ID: Long? = null
		private val TRANSFER_SUM: Double? = null

		private val FROM_WALLET: WalletEntity? = null
		private val TO_WALLET: WalletEntity? = null
		private val CATEGORY: TransactionCategoryEntity? = null
	}

	private val someTransaction = TransactionEntity(
		categoryFkId = CATEGORY_ID,
		dateTime = DATE_TIME,
		dateDay = DATE_DAY,
		describePicturePath = PICTURE_PATH,
		description = DESCRIPTION,
		sumInWalletCurrency = WALLET_SUM,
		transactionId = TRANSACTION_ID,
		fromWalletFkId = FROM_WALLET_ID,
		accountFkId = ACCOUNT_ID,
		sumInAccountCurrency = ACCOUNT_SUM,
		toWalletFkId = TO_WALLET_ID,
		transferSum = TRANSFER_SUM
	)

	private val transactionGroup = TransactionGroupEntity(
		transactionEntity = someTransaction,
		toWalletEntity = TO_WALLET,
		fromWalletEntity = FROM_WALLET,
		categoryEntity = CATEGORY
	)

	@Before
	fun setUp() {
		transactionDao = mockk()
		transactionRepository = TransactionRepositoryImpl(transactionDao)
	}

	@Test
	fun getTransactionById() {

		//Act
		every { transactionDao.getTransactionById(someTransaction.transactionId) } returns Single.just(transactionGroup)
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
		every { transactionDao.deleteTransactionById(someTransaction.transactionId) } returns Completable.complete()
		transactionRepository.deleteTransactionById(someTransaction.transactionId)
		//Assert
		verify { transactionDao.deleteTransactionById(TRANSACTION_ID) }
	}

	@Test
	fun getAllTransactionsByAccountId() {

		//Act
		every { transactionDao.getAllTransactionsByAccountId(ACCOUNT_ID) } returns Flowable.just(listOf(transactionGroup))
		transactionRepository.getAllTransactionsByAccountId(someTransaction.accountFkId)

		//Assert
		verify { transactionDao.getAllTransactionsByAccountId(ACCOUNT_ID) }
	}
}
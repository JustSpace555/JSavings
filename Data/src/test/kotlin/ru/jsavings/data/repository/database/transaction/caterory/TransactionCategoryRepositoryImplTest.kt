package ru.jsavings.data.repository.database.transaction.caterory

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import ru.jsavings.data.database.dao.TransactionCategoryDao
import ru.jsavings.data.entity.database.TransactionCategoryEntity

class TransactionCategoryRepositoryImplTest {

	private lateinit var transactionCategoryDao: TransactionCategoryDao
	private lateinit var transactionCategoryRepository: TransactionCategoryRepository

	private val someCategory = TransactionCategoryEntity(
		accountFkId = 0,
		categoryName = "some name",
		color = 0,
		iconPath = "",
		type = "some type"
	)

	@Before
	fun setUp() {
		transactionCategoryDao = mockk()
		transactionCategoryRepository = TransactionCategoryRepositoryImpl(transactionCategoryDao)
	}

	@Test
	fun insertNewCategory() {

		//Act
		every { transactionCategoryDao.insertNewCategory(someCategory) } returns Single.just(0L)
		transactionCategoryRepository.insertNewCategory(someCategory)

		//Assert
		verify { transactionCategoryDao.insertNewCategory(someCategory) }
	}

	@Test
	fun deleteCategory() {

		//Act
		every { transactionCategoryDao.deleteCategory(someCategory) } returns Completable.complete()
		transactionCategoryRepository.deleteCategory(someCategory)

		//Assert
		verify { transactionCategoryDao.deleteCategory(someCategory) }
	}
}
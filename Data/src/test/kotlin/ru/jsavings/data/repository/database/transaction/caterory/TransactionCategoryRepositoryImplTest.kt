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

	companion object {
		private const val ACCOUNT_ID = 1L
		private const val CATEGORY_NAME = "some name"
		private const val COLOR = 0
		private const val ICON_PATH = ""
		private const val TYPE = "INCOME"
		private const val CATEGORY_ID = 1L
	}

	private val someCategory = TransactionCategoryEntity(
		accountFkId = ACCOUNT_ID,
		categoryName = CATEGORY_NAME,
		color = COLOR,
		iconPath = ICON_PATH,
		type = TYPE,
		categoryId = CATEGORY_ID
	)

	@Before
	fun setUp() {
		transactionCategoryDao = mockk()
		transactionCategoryRepository = TransactionCategoryRepositoryImpl(transactionCategoryDao)
	}

	@Test
	fun insertNewCategory() {

		//Act
		every { transactionCategoryDao.insertNewCategory(someCategory) } returns Single.just(CATEGORY_ID)
		transactionCategoryRepository.insertNewCategory(someCategory)

		//Assert
		verify { transactionCategoryDao.insertNewCategory(someCategory) }
	}

	@Test
	fun getCategoryById() {

		//Act
		every { transactionCategoryDao.getCategoryById(someCategory.categoryId) } returns Single.just(someCategory)
		transactionCategoryRepository.getCategoryById(someCategory.categoryId)

		//Assert
		verify { transactionCategoryDao.getCategoryById(someCategory.categoryId) }
	}

	@Test
	fun getCategoriesByAccountId() {

		//Act
		every { transactionCategoryDao.getCategoriesByAccountId(ACCOUNT_ID) } returns Single.just(listOf(someCategory))
		transactionCategoryRepository.getCategoriesByAccountId(someCategory.categoryId)

		//Assert
		verify { transactionCategoryDao.getCategoriesByAccountId(ACCOUNT_ID) }
	}

	@Test
	fun updateCategory() {

		//Act
		every { transactionCategoryDao.updateCategory(someCategory) } returns Completable.complete()
		transactionCategoryRepository.updateCategory(someCategory)

		//Assert
		verify { transactionCategoryDao.updateCategory(someCategory) }
	}

	@Test
	fun deleteCategory() {

		//Act
		every { transactionCategoryDao.deleteCategoryById(someCategory.categoryId) } returns Completable.complete()
		transactionCategoryRepository.deleteCategoryById(someCategory.categoryId)

		//Assert
		verify { transactionCategoryDao.deleteCategoryById(someCategory.categoryId) }
	}
}
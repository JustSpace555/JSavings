package ru.jsavings.domain.usecase.database.category

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Completable
import org.junit.Before
import org.junit.Test
import ru.jsavings.data.repository.database.transaction.caterory.TransactionCategoryRepository
import ru.jsavings.domain.common.BaseUnitTest

class DeleteTransactionCategoryByIdUseCaseTest : BaseUnitTest() {

	private lateinit var categoryRepository: TransactionCategoryRepository
	private lateinit var useCase: DeleteTransactionCategoryByIdUseCase

	@Before
	fun setUp() {
		categoryRepository = mockk()
		useCase = DeleteTransactionCategoryByIdUseCase(categoryRepository)
	}

	@Test
	operator fun invoke() {

		//Act
		every { categoryRepository.deleteCategoryById(CATEGORY_ID) } returns Completable.complete()
		useCase(CATEGORY_ID).blockingAwait()

		//Assert
		verify { categoryRepository.deleteCategoryById(CATEGORY_ID) }
	}
}
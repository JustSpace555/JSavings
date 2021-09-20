package ru.jsavings.domain.usecase.database.category

import io.mockk.Ordering
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Completable
import org.junit.Before
import org.junit.Test
import ru.jsavings.data.repository.database.transaction.caterory.TransactionCategoryRepository
import ru.jsavings.domain.common.BaseUnitTest
import ru.jsavings.domain.mappers.database.TransactionCategoryMapper

class UpdateTransactionCategoryUseCaseTest : BaseUnitTest() {

	private lateinit var repository: TransactionCategoryRepository
	private lateinit var mapper: TransactionCategoryMapper
	private lateinit var useCase: UpdateTransactionCategoryUseCase

	@Before
	fun setUp() {
		repository = mockk()
		mapper = mockk()
		useCase = UpdateTransactionCategoryUseCase(repository, mapper)
	}

	@Test
	operator fun invoke() {

		//Act
		every { mapper.mapModelToEntity(categoryModel) } returns categoryEntity
		every { repository.updateCategory(categoryEntity) } returns Completable.complete()
		useCase(categoryModel).blockingAwait()

		//Assert
		verify(ordering = Ordering.ORDERED) {
			mapper.mapModelToEntity(categoryModel)
			repository.updateCategory(categoryEntity)
		}
	}
}
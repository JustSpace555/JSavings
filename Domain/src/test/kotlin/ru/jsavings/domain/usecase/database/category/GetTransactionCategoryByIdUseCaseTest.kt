package ru.jsavings.domain.usecase.database.category

import io.mockk.Ordering
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import ru.jsavings.data.repository.database.transaction.caterory.TransactionCategoryRepository
import ru.jsavings.domain.common.BaseUnitTest
import ru.jsavings.domain.mappers.database.TransactionCategoryMapper

class GetTransactionCategoryByIdUseCaseTest : BaseUnitTest() {

	private lateinit var repository: TransactionCategoryRepository
	private lateinit var mapper: TransactionCategoryMapper
	private lateinit var useCase: GetTransactionCategoryByIdUseCase

	@Before
	fun setUp() {
		repository = mockk()
		mapper = mockk()
		useCase = GetTransactionCategoryByIdUseCase(repository, mapper)
	}

	@Test
	operator fun invoke() {

		//Act
		every { repository.getCategoryById(CATEGORY_ID) } returns Single.just(categoryEntity)
		every { mapper.mapEntityToModel(categoryEntity) } returns categoryModel
		useCase(CATEGORY_ID).blockingGet()

		//Assert
		verify(ordering = Ordering.ORDERED) {
			repository.getCategoryById(CATEGORY_ID)
			mapper.mapEntityToModel(categoryEntity)
		}
	}
}
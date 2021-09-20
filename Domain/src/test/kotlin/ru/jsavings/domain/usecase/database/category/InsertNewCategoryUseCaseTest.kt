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

class InsertNewCategoryUseCaseTest : BaseUnitTest() {

	private lateinit var repository: TransactionCategoryRepository
	private lateinit var mapper: TransactionCategoryMapper
	private lateinit var useCase: InsertNewCategoryUseCase

	@Before
	fun setUp() {
		repository = mockk()
		mapper = mockk()
		useCase = InsertNewCategoryUseCase(repository, mapper)
	}

	@Test
	operator fun invoke() {

		//Act
		every { mapper.mapModelToEntity(categoryModel) } returns categoryEntity
		every { repository.insertNewCategory(categoryEntity) } returns Single.just(CATEGORY_ID)
		useCase(categoryModel).blockingGet()

		//Assert
		verify(ordering = Ordering.ORDERED) {
			mapper.mapModelToEntity(categoryModel)
			repository.insertNewCategory(categoryEntity)
		}
	}
}
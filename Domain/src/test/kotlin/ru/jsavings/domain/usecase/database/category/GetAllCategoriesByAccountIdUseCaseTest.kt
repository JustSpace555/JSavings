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

class GetAllCategoriesByAccountIdUseCaseTest : BaseUnitTest() {

	private lateinit var repository: TransactionCategoryRepository
	private lateinit var mapper: TransactionCategoryMapper
	private lateinit var useCase: GetAllCategoriesByAccountIdUseCase

	@Before
	fun setUp() {
		repository = mockk()
		mapper = mockk()
		useCase = GetAllCategoriesByAccountIdUseCase(repository, mapper)
	}

	@Test
	operator fun invoke() {

		//Act
		every { repository.getCategoriesByAccountId(ACCOUNT_ID) } returns Single.just(listOf(categoryEntity))
		every { mapper.mapEntityToModel(categoryEntity) } returns categoryModel
		useCase(ACCOUNT_ID).blockingGet()

		//Assert
		verify(ordering = Ordering.ORDERED) {
			repository.getCategoriesByAccountId(ACCOUNT_ID)
			mapper.mapEntityToModel(categoryEntity)
		}
	}
}
package ru.jsavings.domain.usecase.database.account

import io.mockk.Ordering
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import ru.jsavings.data.entity.database.AccountEntity
import ru.jsavings.data.repository.database.account.AccountRepository
import ru.jsavings.domain.common.BaseUnitTest
import ru.jsavings.domain.mappers.database.AccountMapper
import ru.jsavings.domain.model.database.Account

class GetAllAccountsUseCaseTest : BaseUnitTest() {

	private lateinit var repository: AccountRepository
	private lateinit var mapper: AccountMapper
	private lateinit var useCase: GetAllAccountsUseCase

	@Before
	fun setUp() {
		repository = mockk()
		mapper = mockk()
		useCase = GetAllAccountsUseCase(repository, mapper)
	}

	@Test
	operator fun invoke() {

		//Act
		every { repository.getAllAccounts() } returns Single.just(listOf(accountEntity))
		every { mapper.mapEntityToModel(accountEntity) } returns accountModel
		useCase().blockingGet()

		//Assert
		verify(ordering = Ordering.SEQUENCE) {
			repository.getAllAccounts()
			mapper.mapEntityToModel(accountEntity)
		}
	}
}
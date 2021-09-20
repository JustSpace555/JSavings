package ru.jsavings.domain.usecase.database.account

import io.mockk.Ordering
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Flowable

import org.junit.Before
import org.junit.Test
import ru.jsavings.data.repository.database.account.AccountRepository
import ru.jsavings.domain.common.BaseUnitTest
import ru.jsavings.domain.mappers.database.AccountMapper

class GetAccountByIdUseCaseTest : BaseUnitTest() {

	private lateinit var usecase: GetAccountByIdUseCase
	private lateinit var accountRepository: AccountRepository
	private lateinit var accountMapper: AccountMapper

	@Before
	fun setUp() {
		accountRepository = mockk()
		accountMapper = mockk()
		usecase = GetAccountByIdUseCase(accountRepository, accountMapper)
	}

	@Test
	operator fun invoke() {

		//Act
		every { accountRepository.getAccountByIdFlowable(ACCOUNT_ID) } returns Flowable.just(accountEntity)
		every { accountMapper.mapEntityToModel(accountEntity) } returns accountModel
		usecase(ACCOUNT_ID).blockingSingle()

		//Assert
		verify(ordering = Ordering.ORDERED) {
			accountRepository.getAccountByIdFlowable(ACCOUNT_ID)
			accountMapper.mapEntityToModel(accountEntity)
		}
	}
}
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
import ru.jsavings.domain.mappers.database.AccountMapper
import ru.jsavings.domain.model.database.Account

class InsertAccountUseCaseTest {

	companion object {
		private const val ID = 1L
		private const val NAME = "some name"
		private const val CURRENCY = "some currency"
		private const val BALANCE = 25.0
	}

	private val someAccountEntity = AccountEntity(
		accountId = ID,
		accountName = NAME,
		mainCurrencyCode = CURRENCY,
		balanceInMainCurrency = BALANCE
	)

	private val someAccountModel = Account(
		accountId = ID,
		name = NAME,
		mainCurrencyCode = CURRENCY,
		balanceInMainCurrency = BALANCE
	)

	private lateinit var repository: AccountRepository
	private lateinit var mapper: AccountMapper
	private lateinit var useCase: InsertAccountUseCase

	@Before
	fun setUp() {
		repository = mockk()
		mapper = mockk()
		useCase = InsertAccountUseCase(repository, mapper)
	}

	@Test
	operator fun invoke() {

		//Act
		every { repository.insertNewAccount(someAccountEntity) } returns Single.just(someAccountEntity.accountId)
		every { mapper.mapModelToEntity(someAccountModel) } returns someAccountEntity
		useCase(someAccountModel).blockingGet()

		//Assert
		verify(ordering = Ordering.SEQUENCE) {
			mapper.mapModelToEntity(someAccountModel)
			repository.insertNewAccount(someAccountEntity)
		}
	}
}
package ru.jsavings.domain.usecase.database.transaction

import io.mockk.Ordering
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Flowable
import org.junit.Before
import org.junit.Test
import ru.jsavings.data.repository.database.transaction.TransactionRepository
import ru.jsavings.domain.common.BaseUnitTest
import ru.jsavings.domain.mappers.database.TransactionMapper

class GetAllTransactionsByAccountIdUseCaseTest : BaseUnitTest() {

	private lateinit var transactionRepository: TransactionRepository
	private lateinit var transactionMapper: TransactionMapper
	private lateinit var useCase: GetAllTransactionsByAccountIdUseCase

	@Before
	fun setUp() {
		transactionRepository = mockk()
		transactionMapper = mockk()
		useCase = GetAllTransactionsByAccountIdUseCase(transactionRepository, transactionMapper)
	}

	@Test
	operator fun invoke() {

		//Act
		every { transactionRepository.getAllTransactionsByAccountId(ACCOUNT_ID) } returns
				Flowable.just(listOf(transactionGroupEntity))
		every { transactionMapper.mapEntityToModel(transactionGroupEntity) } returns transactionModel
		useCase(ACCOUNT_ID).blockingSingle()

		//Assert
		verify(Ordering.ORDERED) {
			transactionRepository.getAllTransactionsByAccountId(ACCOUNT_ID)
			transactionMapper.mapEntityToModel(transactionGroupEntity)
		}
	}
}
package ru.jsavings.domain.usecase.database.transaction

import io.mockk.Ordering
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import ru.jsavings.data.repository.database.transaction.TransactionRepository
import ru.jsavings.domain.common.BaseUnitTest
import ru.jsavings.domain.mappers.database.TransactionMapper

class GetTransactionByIdUseCaseTest : BaseUnitTest() {

	private lateinit var transactionRepository: TransactionRepository
	private lateinit var transactionMapper: TransactionMapper
	private lateinit var useCase: GetTransactionByIdUseCase

	@Before
	fun setUp() {
		transactionRepository = mockk()
		transactionMapper = mockk()
		useCase = GetTransactionByIdUseCase(transactionRepository, transactionMapper)
	}

	@Test
	operator fun invoke() {

		//Act
		every { transactionRepository.getTransactionById(TRANSACTION_ID) } returns Single.just(transactionGroupEntity)
		every { transactionMapper.mapEntityToModel(transactionGroupEntity) } returns transactionModel
		useCase(TRANSACTION_ID).blockingGet()

		//Assert
		verify(Ordering.ORDERED) {
			transactionRepository.getTransactionById(TRANSACTION_ID)
			transactionMapper.mapEntityToModel(transactionGroupEntity)
		}
	}
}
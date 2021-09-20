package ru.jsavings.domain.usecase.database.transaction

import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import ru.jsavings.data.repository.database.account.AccountRepository
import ru.jsavings.data.repository.database.transaction.TransactionRepository
import ru.jsavings.data.repository.database.wallet.WalletRepository
import ru.jsavings.data.repository.network.currency.CurrencyRepository
import ru.jsavings.domain.common.BaseUnitTest
import ru.jsavings.domain.mappers.database.TransactionMapper

class UpdateTransactionUseCaseTest : BaseUnitTest() {

	companion object {
		private const val ANOTHER_ID = WALLET_ID + 1
	}

	private lateinit var transactionRepository: TransactionRepository
	private lateinit var transactionMapper: TransactionMapper
	private lateinit var walletRepository: WalletRepository
	private lateinit var accountRepository: AccountRepository
	private lateinit var currencyRepository: CurrencyRepository
	private lateinit var useCase: UpdateTransactionUseCase

	@Before
	fun setUp() {
		transactionRepository = mockk()
		transactionMapper = mockk()
		walletRepository = mockk()
		accountRepository = mockk()
		currencyRepository = mockk()
		useCase = UpdateTransactionUseCase(transactionRepository, transactionMapper,
			currencyRepository, walletRepository, accountRepository
		)
	}

	@Test
	fun `Update INCOME to INCOME with wallets same id`() {

		//Arrange

	}
}
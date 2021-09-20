package ru.jsavings.domain.usecase.database.transaction

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import ru.jsavings.data.entity.network.ConversionEntity
import ru.jsavings.data.entity.network.QueryInfo
import ru.jsavings.data.entity.network.RateInfo
import ru.jsavings.data.repository.database.account.AccountRepository
import ru.jsavings.data.repository.database.transaction.TransactionRepository
import ru.jsavings.data.repository.database.wallet.WalletRepository
import ru.jsavings.data.repository.network.currency.CurrencyRepository
import ru.jsavings.domain.common.BaseUnitTest
import ru.jsavings.domain.mappers.database.TransactionMapper
import ru.jsavings.domain.mappers.database.WalletMapper
import ru.jsavings.domain.model.database.category.TransactionCategoryType

class SaveNewTransactionUseCaseTest : BaseUnitTest() {

	companion object {
		private const val FIRST_WALLET_ID = 1L
		private const val SECOND_WALLET_ID = 2L
		private const val FROM_CURRENCY = "FROM"
		private const val TO_CURRENCY = "TO"
		private const val CONVERSION_RESULT = 10.0
	}

	private lateinit var accountRepository: AccountRepository
	private lateinit var walletRepository: WalletRepository
	private lateinit var transactionRepository: TransactionRepository
	private lateinit var currencyRepository: CurrencyRepository
	private lateinit var walletMapper: WalletMapper
	private lateinit var transactionMapper: TransactionMapper
	private lateinit var useCase: SaveNewTransactionUseCase

	@Before
	fun setUp() {
		accountRepository = mockk()
		walletRepository = mockk()
		transactionRepository = mockk()
		currencyRepository = mockk()
		walletMapper = mockk()
		transactionMapper = mockk()
		useCase = SaveNewTransactionUseCase(
			accountRepository, walletRepository, transactionRepository,
			currencyRepository, walletMapper, transactionMapper
		)
	}

	@Test
	fun `Save new transfer transaction with same wallets`() {

		//Arrange
		val transactionModelCopy = transactionModel.copy(category = null)
		val newTransactionModel = transactionModel.copy(
			transferSum = WALLET_SUM, sumInAccountCurrency = 0.0, category = null
		)
		val newTransactionEntity = transactionEntity.copy(
			transferSum = WALLET_SUM, sumInAccountCurrency = 0.0, categoryFkId = null
		)

		//Act
		every { transactionMapper.mapModelToTransactionEntity(newTransactionModel) } returns
			newTransactionEntity
		every { transactionRepository.insertNewTransaction(newTransactionEntity) } returns
			Single.just(TRANSACTION_ID)
		useCase(transactionModelCopy).blockingGet()

		//Assert
		verify(exactly = 0) {
			walletRepository.updateWallet(any())
			accountRepository.updateAccount(any())
		}
		verify { transactionRepository.insertNewTransaction(newTransactionEntity) }
	}

	@Test
	fun `Save new transfer transaction with different wallets`() {

		//Arrange
		val fromWalletModelCopy = walletModel.copy(
			walletId = FIRST_WALLET_ID,
			currency = FROM_CURRENCY
		)
		val toWalletModelCopy = walletModel.copy(
			walletId = SECOND_WALLET_ID,
			currency = TO_CURRENCY
		)
		val transactionModelCopy = transactionModel.copy(
			fromWallet = fromWalletModelCopy, toWallet = toWalletModelCopy, category = null
		)
		val conversionCopy = ConversionEntity(
			info = RateInfo(0.0),
			query = QueryInfo(
				FROM_CURRENCY, TO_CURRENCY, WALLET_SUM
			),
			result = CONVERSION_RESULT,
			success = true
		)
		val newFromWalletModel = fromWalletModelCopy.copy(
			balance = WALLET_BALANCE - WALLET_SUM
		)
		val newToWalletModel = toWalletModelCopy.copy(
			balance = WALLET_BALANCE + CONVERSION_RESULT
		)
		val newFromWalletEntity = walletEntity.copy(
			walletId = FIRST_WALLET_ID, balance = WALLET_BALANCE - WALLET_SUM
		)
		val newToWalletEntity = walletEntity.copy(
			walletId = SECOND_WALLET_ID, balance = WALLET_BALANCE + CONVERSION_RESULT
		)
		val newTransactionModel = transactionModelCopy.copy(
			transferSum = CONVERSION_RESULT, sumInAccountCurrency = 0.0
		)
		val newTransactionEntity = transactionEntity.copy(
			categoryFkId = null, fromWalletFkId = FIRST_WALLET_ID, toWalletFkId = SECOND_WALLET_ID,
			transferSum = CONVERSION_RESULT, sumInAccountCurrency = 0.0
		)

		//Act
		every { currencyRepository.getConversion(
			FROM_CURRENCY, TO_CURRENCY, WALLET_SUM, any()
		)} returns Single.just(conversionCopy)
		every { walletMapper.mapModelToEntity(newFromWalletModel) } returns newFromWalletEntity
		every { walletMapper.mapModelToEntity(newToWalletModel) } returns newToWalletEntity
		every { walletRepository.updateWallet(newFromWalletEntity) } returns Completable.complete()
		every { walletRepository.updateWallet(newToWalletEntity) } returns Completable.complete()
		every { transactionMapper.mapModelToTransactionEntity(newTransactionModel) } returns
			newTransactionEntity
		every { transactionRepository.insertNewTransaction(newTransactionEntity) } returns
			Single.just(TRANSACTION_ID)
		useCase(transactionModelCopy).blockingGet()

		//Assert
		verify(exactly = 0) { accountRepository.updateAccount(any()) }
		verify {
			walletRepository.updateWallet(newFromWalletEntity)
			walletRepository.updateWallet(newToWalletEntity)
			transactionRepository.insertNewTransaction(newTransactionEntity)
		}
	}

	@Test
	fun `Save new income transaction`() {

		//Arrange
		val accountEntityCopy = accountEntity.copy(mainCurrencyCode = TO_CURRENCY)
		val walletModelCopy = walletModel.copy(currency = FROM_CURRENCY)
		val walletEntityCopy = walletEntity.copy(currencyCode = FROM_CURRENCY)
		val transactionModelCopy = transactionModel.copy(
			toWallet = walletModelCopy,
			category = categoryModel
		)
		val conversionEntity = ConversionEntity(
			info = RateInfo(0.0),
			query = QueryInfo(
				FROM_CURRENCY, TO_CURRENCY, transactionModelCopy.sumInWalletCurrency
			),
			result = CONVERSION_RESULT,
			success = true
		)

		val newAccountEntity = accountEntity.copy(
			mainCurrencyCode = TO_CURRENCY,
			balanceInMainCurrency = ACCOUNT_BALANCE + CONVERSION_RESULT
		)
		val newWalletModel = walletModelCopy.copy(balance = WALLET_BALANCE + WALLET_SUM)
		val newWalletEntity = walletEntityCopy.copy(balance = WALLET_BALANCE + WALLET_SUM)
		val newTransactionModel = transactionModelCopy.copy(
			transferSum = null,
			sumInAccountCurrency = CONVERSION_RESULT,
			fromWallet = null
		)
		val newTransactionEntity = transactionEntity.copy(
			transferSum = null,
			sumInAccountCurrency = CONVERSION_RESULT,
			fromWalletFkId = null
		)

		//Act
		every { accountRepository.getAccountByIdSingle(ACCOUNT_ID) } returns
			Single.just(accountEntityCopy)
		every {
			currencyRepository.getConversion(
				FROM_CURRENCY, TO_CURRENCY, transactionModelCopy.sumInWalletCurrency, any()
			)
		} returns Single.just(conversionEntity)
		every { accountRepository.updateAccount(newAccountEntity) } returns Completable.complete()
		every { walletMapper.mapModelToEntity(newWalletModel) } returns newWalletEntity
		every { walletRepository.updateWallet(newWalletEntity) } returns Completable.complete()
		every { transactionMapper.mapModelToTransactionEntity(newTransactionModel) } returns
			newTransactionEntity
		every { transactionRepository.insertNewTransaction(newTransactionEntity) } returns Single.just(TRANSACTION_ID)
		useCase(transactionModelCopy).blockingGet()

		//Assert
		verify {
			accountRepository.updateAccount(newAccountEntity)
			walletRepository.updateWallet(newWalletEntity)
			transactionRepository.insertNewTransaction(newTransactionEntity)
		}
	}

	@Test
	fun `Save new consumption transaction`() {

		//Arrange
		val accountEntityCopy = accountEntity.copy(mainCurrencyCode = TO_CURRENCY)
		val walletModelCopy = walletModel.copy(currency = FROM_CURRENCY)
		val walletEntityCopy = walletEntity.copy(currencyCode = FROM_CURRENCY)
		val transactionModelCopy = transactionModel.copy(
			fromWallet = walletModelCopy,
			category = categoryModel.copy(categoryType = TransactionCategoryType.CONSUMPTION)
		)
		val conversionEntity = ConversionEntity(
			info = RateInfo(0.0),
			query = QueryInfo(
				FROM_CURRENCY, TO_CURRENCY, transactionModelCopy.sumInWalletCurrency
			),
			result = CONVERSION_RESULT,
			success = true
		)

		val newAccountEntity = accountEntity.copy(
			mainCurrencyCode = TO_CURRENCY,
			balanceInMainCurrency = ACCOUNT_BALANCE - CONVERSION_RESULT
		)
		val newWalletModel = walletModelCopy.copy(balance = WALLET_BALANCE - WALLET_SUM)
		val newWalletEntity = walletEntityCopy.copy(balance = WALLET_BALANCE - WALLET_SUM)
		val newTransactionModel = transactionModelCopy.copy(
			transferSum = null,
			sumInAccountCurrency = CONVERSION_RESULT,
			toWallet = null
		)
		val newTransactionEntity = transactionEntity.copy(
			transferSum = null,
			sumInAccountCurrency = CONVERSION_RESULT,
			toWalletFkId = null
		)

		//Act
		every { accountRepository.getAccountByIdSingle(ACCOUNT_ID) } returns
			Single.just(accountEntityCopy)
		every {
			currencyRepository.getConversion(
				FROM_CURRENCY, TO_CURRENCY, transactionModelCopy.sumInWalletCurrency, any()
			)
		} returns Single.just(conversionEntity)
		every { accountRepository.updateAccount(newAccountEntity) } returns Completable.complete()
		every { walletMapper.mapModelToEntity(newWalletModel) } returns newWalletEntity
		every { walletRepository.updateWallet(newWalletEntity) } returns Completable.complete()
		every { transactionMapper.mapModelToTransactionEntity(newTransactionModel) } returns
			newTransactionEntity
		every { transactionRepository.insertNewTransaction(newTransactionEntity) } returns Single.just(TRANSACTION_ID)
		useCase(transactionModelCopy).blockingGet()

		//Assert
		verify {
			accountRepository.updateAccount(newAccountEntity)
			walletRepository.updateWallet(newWalletEntity)
			transactionRepository.insertNewTransaction(newTransactionEntity)
		}
	}
}
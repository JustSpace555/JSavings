package ru.jsavings.domain.usecase.database.wallet

import io.mockk.Ordering
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import ru.jsavings.data.entity.database.AccountEntity
import ru.jsavings.data.entity.database.WalletEntity
import ru.jsavings.data.entity.network.ConversionEntity
import ru.jsavings.data.entity.network.QueryInfo
import ru.jsavings.data.entity.network.RateInfo
import ru.jsavings.data.repository.database.account.AccountRepository
import ru.jsavings.data.repository.database.wallet.WalletRepository
import ru.jsavings.data.repository.network.currency.CurrencyRepository
import ru.jsavings.domain.mappers.database.WalletMapper
import ru.jsavings.domain.model.database.wallet.Wallet
import ru.jsavings.domain.model.database.wallet.WalletCategoryType
import ru.jsavings.domain.model.network.ConversionInfo

class InsertNewWalletUseCaseTest {

	companion object {
		private const val ACCOUNT_ID = 1L
		private const val ACCOUNT_NAME = "some account name"
		private const val ACCOUNT_CURRENCY = "EUR"
		private const val ACCOUNT_BALANCE = 20.0

		private const val BALANCE = 25.0
		private val CATEGORY = WalletCategoryType.CASH
		private const val COLOR = 0
		private const val CREDIT_LIMIT = 0.0
		private const val GRACE_PERIOD = 0
		private const val CURRENCY_CODE = "USD"
		private const val ICON_PATH = ""
		private const val INTEREST_RATE = 0.0
		private const val PAYMENT_DAY = 0
		private const val NAME = "some name"
		private const val ID = 1L

		private const val FROM = CURRENCY_CODE
		private const val TO = ACCOUNT_CURRENCY
		private const val AMOUNT = BALANCE
		private const val RESULT = 5.0
		private const val PRECISION = 2
	}

	private val someAccountEntity = AccountEntity(
		accountId = ACCOUNT_ID,
		accountName = ACCOUNT_NAME,
		mainCurrencyCode = ACCOUNT_CURRENCY,
		balanceInMainCurrency = ACCOUNT_BALANCE
	)

	private val someWalletModel = Wallet(
		accountId = ACCOUNT_ID,
		balance = BALANCE,
		category = CATEGORY,
		color = COLOR,
		creditLimit = CREDIT_LIMIT,
		gracePeriod = GRACE_PERIOD,
		currency = CURRENCY_CODE,
		iconPath = ICON_PATH,
		interestRate = INTEREST_RATE,
		paymentDay = PAYMENT_DAY,
		name = NAME,
		walletId = ID
	)

	private val someWalletEntity = WalletEntity(
		accountFkId = ACCOUNT_ID,
		balance = BALANCE,
		category = CATEGORY.toString(),
		color = COLOR,
		creditLimit = CREDIT_LIMIT,
		gracePeriod = GRACE_PERIOD,
		currencyCode = CURRENCY_CODE,
		iconPath = ICON_PATH,
		interestRate = INTEREST_RATE,
		paymentDay = PAYMENT_DAY,
		walletName = NAME,
		walletId = ID
	)

	private val someConversionEntity = ConversionEntity(
		query = QueryInfo(
			from = FROM,
			to = TO,
			amount = AMOUNT
		),
		info = RateInfo(0.0),
		result = RESULT,
		success = true
	)

	private lateinit var walletRepository: WalletRepository
	private lateinit var accountRepository: AccountRepository
	private lateinit var currencyRepository: CurrencyRepository
	private lateinit var mapper: WalletMapper
	private lateinit var useCase: InsertNewWalletUseCase

	@Before
	fun setUp() {
		walletRepository = mockk()
		accountRepository = mockk()
		currencyRepository = mockk()
		mapper = mockk()
		useCase = InsertNewWalletUseCase(walletRepository, accountRepository, currencyRepository, mapper)

		every { mapper.mapModelToEntity(someWalletModel) } returns someWalletEntity
		every { walletRepository.insertNewWallet(someWalletEntity) } returns Single.just(ID)
	}

	@Test
	fun invokeWithConversionTest() {

		//Arrange
		val newAccount = someAccountEntity.copy(balanceInMainCurrency = someAccountEntity.balanceInMainCurrency + RESULT)

		//Act
		every { accountRepository.updateAccount(newAccount) } returns Completable.complete()
		every { currencyRepository.getConversion(FROM, TO, AMOUNT, PRECISION) } returns Single.just(someConversionEntity)
		every { accountRepository.getAccountById(someWalletModel.accountId) } returns Single.just(someAccountEntity)
		useCase(someWalletModel).blockingAwait()

		//Assert
		verify(ordering = Ordering.ORDERED) {
			walletRepository.insertNewWallet(someWalletEntity)
			currencyRepository.getConversion(FROM, TO, AMOUNT, PRECISION)
			accountRepository.updateAccount(newAccount)
		}
	}

	@Test
	fun invokeWithoutConversion() {

		//Arrange
		val newAccount = someAccountEntity.copy(
			mainCurrencyCode = CURRENCY_CODE,
			balanceInMainCurrency = someAccountEntity.balanceInMainCurrency + BALANCE
		)

		//Act
		every { accountRepository.updateAccount(newAccount) } returns Completable.complete()
		every { accountRepository.getAccountById(someWalletModel.accountId) } returns
				Single.just(someAccountEntity.copy(mainCurrencyCode = CURRENCY_CODE))
		useCase(someWalletModel).blockingAwait()

		//Assert
		verify(ordering = Ordering.ORDERED) {
			walletRepository.insertNewWallet(someWalletEntity)
			accountRepository.updateAccount(newAccount)
		}
	}
}
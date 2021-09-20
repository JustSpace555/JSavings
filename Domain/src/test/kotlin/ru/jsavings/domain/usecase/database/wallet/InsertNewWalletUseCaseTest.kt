package ru.jsavings.domain.usecase.database.wallet

import io.mockk.Ordering
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
import ru.jsavings.data.repository.database.wallet.WalletRepository
import ru.jsavings.data.repository.network.currency.CurrencyRepository
import ru.jsavings.domain.common.BaseUnitTest
import ru.jsavings.domain.mappers.database.WalletMapper

class InsertNewWalletUseCaseTest : BaseUnitTest() {

	companion object {

		private const val FROM = WALLET_CURRENCY_CODE
		private const val TO = ACCOUNT_CURRENCY
		private const val AMOUNT = WALLET_BALANCE
		private const val RESULT = 5.0
		private const val PRECISION = 2
	}

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

		every { mapper.mapModelToEntity(walletModel) } returns walletEntity
		every { walletRepository.insertNewWallet(walletEntity) } returns Single.just(WALLET_ID)
	}

	@Test
	fun invokeWithConversionTest() {

		//Arrange
		val newAccount = accountEntity.copy(balanceInMainCurrency = accountEntity.balanceInMainCurrency + RESULT)

		//Act
		every { accountRepository.updateAccount(newAccount) } returns Completable.complete()
		every { currencyRepository.getConversion(FROM, TO, AMOUNT, PRECISION) } returns Single.just(someConversionEntity)
		every { accountRepository.getAccountByIdSingle(walletModel.accountId) } returns Single.just(accountEntity)
		useCase(walletModel).blockingGet()

		//Assert
		verify(ordering = Ordering.ORDERED) {
			walletRepository.insertNewWallet(walletEntity)
			currencyRepository.getConversion(FROM, TO, AMOUNT, PRECISION)
			accountRepository.updateAccount(newAccount)
		}
	}

	@Test
	fun invokeWithoutConversion() {

		//Arrange
		val newAccount = accountEntity.copy(
			mainCurrencyCode = WALLET_CURRENCY_CODE,
			balanceInMainCurrency = accountEntity.balanceInMainCurrency + WALLET_BALANCE
		)

		//Act
		every { accountRepository.updateAccount(newAccount) } returns Completable.complete()
		every { accountRepository.getAccountByIdSingle(walletModel.accountId) } returns
				Single.just(accountEntity.copy(mainCurrencyCode = WALLET_CURRENCY_CODE))
		useCase(walletModel).blockingGet()

		//Assert
		verify(ordering = Ordering.ORDERED) {
			walletRepository.insertNewWallet(walletEntity)
			accountRepository.updateAccount(newAccount)
		}
	}
}
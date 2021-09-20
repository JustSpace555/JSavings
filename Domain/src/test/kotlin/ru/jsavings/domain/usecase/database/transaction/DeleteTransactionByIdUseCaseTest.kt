package ru.jsavings.domain.usecase.database.transaction

import io.mockk.Ordering
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import ru.jsavings.data.repository.database.account.AccountRepository
import ru.jsavings.data.repository.database.transaction.TransactionRepository
import ru.jsavings.data.repository.database.wallet.WalletRepository

import ru.jsavings.domain.common.BaseUnitTest
import ru.jsavings.domain.model.database.category.TransactionCategoryType

class DeleteTransactionByIdUseCaseTest : BaseUnitTest() {

	companion object {
		private const val FIRST_ID = 1L
		private const val SECOND_ID = 2L
	}

	private val fromWalletSame = walletEntity.copy(walletId = FIRST_ID)
	private val toWalletSame = walletEntity.copy(walletId = FIRST_ID)
	private val fromWalletDifferent = walletEntity.copy(walletId = FIRST_ID)
	private val toWalletDifferent = walletEntity.copy(walletId = SECOND_ID)

	private lateinit var walletRepository: WalletRepository
	private lateinit var accountRepository: AccountRepository
	private lateinit var transactionRepository: TransactionRepository
	private lateinit var useCase: DeleteTransactionByIdUseCase

	@Before
	fun setUp() {
		walletRepository = mockk()
		accountRepository = mockk()
		transactionRepository = mockk()
		useCase = DeleteTransactionByIdUseCase(transactionRepository, walletRepository, accountRepository)

		every { transactionRepository.deleteTransactionById(TRANSACTION_ID) } returns Completable.complete()
	}

	@Test
	fun deleteTransferWithSameWallets() {

		//Arrange
		val transactionGroup = transactionGroupEntity.copy(
			fromWalletEntity = fromWalletSame,
			toWalletEntity = toWalletSame,
			categoryEntity = null
		)

		//Act
		every { transactionRepository.getTransactionById(TRANSACTION_ID) } returns Single.just(transactionGroup)
		every { accountRepository.getAccountByIdSingle(ACCOUNT_ID) } returns Single.just(accountEntity)
		useCase(TRANSACTION_ID).blockingAwait()

		//Assert
		verify(exactly = 0) {
			walletRepository.updateWallet(any())
			accountRepository.updateAccount(accountEntity)
		}
		verify { transactionRepository.deleteTransactionById(TRANSACTION_ID) }
	}

	@Test
	fun deleteTransferWithDifferentWallets() {
		//Arrange
		val transactionGroup = transactionGroupEntity.copy(
			fromWalletEntity = fromWalletDifferent,
			toWalletEntity = toWalletDifferent,
			categoryEntity = null
		)
		val newFromWalletDifferent = fromWalletDifferent.copy(
			balance = fromWalletDifferent.balance + transactionGroup.transactionEntity.sumInWalletCurrency
		)
		val newToWalletDifferent = toWalletDifferent.copy(
			balance = toWalletDifferent.balance - transactionGroup.transactionEntity.transferSum!!
		)

		//Act
		every { transactionRepository.getTransactionById(TRANSACTION_ID) } returns Single.just(transactionGroup)
		every { accountRepository.getAccountByIdSingle(ACCOUNT_ID) } returns Single.just(accountEntity)
		every { walletRepository.updateWallet(any()) } returns Completable.complete()
		useCase(TRANSACTION_ID).blockingAwait()

		//Assert
		verify(exactly = 0) { accountRepository.updateAccount(accountEntity) }
		verify {
			transactionRepository.deleteTransactionById(TRANSACTION_ID)
			walletRepository.updateWallet(newFromWalletDifferent)
			walletRepository.updateWallet(newToWalletDifferent)
		}
	}

	@Test
	fun deleteIncome() {

		//Arrange
		val transactionGroup = transactionGroupEntity.copy(
			fromWalletEntity = null,
			toWalletEntity = toWalletSame,
			categoryEntity = categoryEntity
		)
		val newToWallet = toWalletSame.copy(
			balance = toWalletSame.balance - transactionGroup.transactionEntity.sumInWalletCurrency
		)
		val newAccount = accountEntity.copy(balanceInMainCurrency =
			accountEntity.balanceInMainCurrency - transactionGroup.transactionEntity.sumInAccountCurrency
		)

		//Act
		every { transactionRepository.getTransactionById(TRANSACTION_ID) } returns Single.just(transactionGroup)
		every { walletRepository.updateWallet(newToWallet) } returns Completable.complete()
		every { accountRepository.getAccountByIdSingle(ACCOUNT_ID) } returns Single.just(accountEntity)
		every { accountRepository.updateAccount(newAccount) } returns Completable.complete()
		useCase(TRANSACTION_ID).blockingAwait()

		//Assert
		verify {
			walletRepository.updateWallet(newToWallet)
			accountRepository.updateAccount(newAccount)
			transactionRepository.deleteTransactionById(TRANSACTION_ID)
		}
	}

	@Test
	fun deleteConsumption() {

		//Arrange
		val transactionGroup = transactionGroupEntity.copy(
			fromWalletEntity = fromWalletSame,
			toWalletEntity = null,
			categoryEntity = categoryEntity.copy(type = TransactionCategoryType.CONSUMPTION.toString())
		)
		val newFromWallet = fromWalletSame.copy(
			balance = fromWalletSame.balance + transactionGroup.transactionEntity.sumInWalletCurrency
		)
		val newAccount = accountEntity.copy(balanceInMainCurrency =
			accountEntity.balanceInMainCurrency + transactionGroup.transactionEntity.sumInAccountCurrency
		)

		//Act
		every { transactionRepository.getTransactionById(TRANSACTION_ID) } returns Single.just(transactionGroup)
		every { walletRepository.updateWallet(newFromWallet) } returns Completable.complete()
		every { accountRepository.getAccountByIdSingle(ACCOUNT_ID) } returns Single.just(accountEntity)
		every { accountRepository.updateAccount(newAccount) } returns Completable.complete()
		useCase(TRANSACTION_ID).blockingAwait()

		//Assert
		verify {
			walletRepository.updateWallet(newFromWallet)
			accountRepository.updateAccount(newAccount)
			transactionRepository.deleteTransactionById(TRANSACTION_ID)
		}
	}
}
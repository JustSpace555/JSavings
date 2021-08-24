package ru.jsavings.data.repository.database.wallet

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import ru.jsavings.data.database.dao.WalletDao
import ru.jsavings.data.entity.database.WalletEntity

class WalletRepositoryImplTest {

	private lateinit var walletDao: WalletDao
	private lateinit var walletRepository: WalletRepository

	private val someWallet = WalletEntity(
		accountFkId = 1,
		balance = 25.0,
		category = "some category",
		color = 0,
		creditLimit = 0.0,
		gracePeriod = 0,
		currencyCode = "USD",
		iconPath = "",
		interestRate = 0.0,
		paymentDay = 0,
		walletName = "some name",
		walletId = 1
	)

	@Before
	fun setUp() {
		walletDao = mockk()
		walletRepository = WalletRepositoryImpl(walletDao)
	}

	@Test
	fun getWalletsByAccountId() {

		//Act
		every { walletDao.getWalletsByAccountId(someWallet.accountFkId) } returns Single.just(listOf(someWallet))
		walletRepository.getWalletsByAccountId(someWallet.accountFkId)

		//Assert
		verify { walletDao.getWalletsByAccountId(someWallet.accountFkId) }
	}

	@Test
	fun insertNewWallet() {

		//Act
		every { walletDao.insertNewWallet(someWallet) } returns Single.just(someWallet.walletId)
		walletRepository.insertNewWallet(someWallet)

		//Assert
		verify { walletDao.insertNewWallet(someWallet) }
	}

	@Test
	fun updateWallet() {

		//Act
		every { walletDao.updateWallet(someWallet) } returns Completable.complete()
		walletRepository.updateWallet(someWallet)

		//Assert
		verify { walletDao.updateWallet(someWallet) }
	}

	@Test
	fun deleteWallet() {

		//Act
		every { walletDao.deleteWallet(someWallet) } returns Completable.complete()
		walletRepository.deleteWallet(someWallet)

		//Assert
		verify { walletDao.deleteWallet(someWallet) }
	}
}
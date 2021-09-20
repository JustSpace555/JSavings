package ru.jsavings.domain.usecase.database.wallet

import io.mockk.Ordering
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import ru.jsavings.data.repository.database.wallet.WalletRepository
import ru.jsavings.domain.common.BaseUnitTest
import ru.jsavings.domain.mappers.database.WalletMapper

class GetWalletByIdUseCaseTest : BaseUnitTest() {

	private lateinit var walletRepository: WalletRepository
	private lateinit var walletMapper: WalletMapper
	private lateinit var useCase: GetWalletByIdUseCase

	@Before
	fun setUp() {
		walletRepository = mockk()
		walletMapper = mockk()
		useCase = GetWalletByIdUseCase(walletRepository, walletMapper)
	}

	@Test
	operator fun invoke() {

		//Act
		every { walletRepository.getWalletById(WALLET_ID) } returns Single.just(walletEntity)
		every { walletMapper.mapEntityToModel(walletEntity) } returns walletModel
		useCase(WALLET_ID).blockingGet()

		//Assert
		verify(ordering = Ordering.ORDERED) {
			walletRepository.getWalletById(WALLET_ID)
			walletMapper.mapEntityToModel(walletEntity)
		}
	}
}
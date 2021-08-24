package ru.jsavings.data.repository.network.crypto

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import ru.jsavings.data.entity.network.CryptoCoinEntity
import ru.jsavings.data.network.api.exchangerate.ExchangeRateApi

class CryptoRepositoryImplTest {

	private lateinit var api: ExchangeRateApi
	private lateinit var cryptoRepository: CryptoRepository

	private val someCoin = CryptoCoinEntity(
		success = true,
		cryptocurrencies = mapOf()
	)

	@Before
	fun setUp() {
		api = mockk()
		cryptoRepository = CryptoRepositoryImpl(api)
	}

	@Test
	fun getAvailableCryptoCoins() {

		//Act
		every { api.getCryptoCoins() } returns Single.just(someCoin)
		cryptoRepository.getAvailableCryptoCoins()

		//Assert
		verify { api.getCryptoCoins() }
	}
}
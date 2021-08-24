package ru.jsavings.domain.usecase.network

import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import ru.jsavings.data.entity.network.CryptoCoinEntity
import ru.jsavings.data.repository.network.crypto.CryptoRepository
import ru.jsavings.domain.mappers.network.CryptoCoinsMapper
import ru.jsavings.domain.model.network.crypto.CryptoCoin

class GetCryptoCoinsUseCaseTest {

	private lateinit var repository: CryptoRepository
	private lateinit var mapper: CryptoCoinsMapper
	private lateinit var useCase: GetCryptoCoinsUseCase

	private val someCryptoCoinEntity = CryptoCoinEntity(
		success = true,
		cryptocurrencies = mapOf()
	)

	private val firstCoin = CryptoCoin(
		id = "1",
		name = "some name",
		symbol = "some symbol"
	)

	private val secondCoin = CryptoCoin(
		id = "2",
		name = "some name",
		symbol = "some symbol"
	)

	@Before
	fun setUp() {
		repository = mockk()
		mapper = mockk()
		useCase = GetCryptoCoinsUseCase(repository, mapper)

		every { repository.getAvailableCryptoCoins() } returns Single.just(someCryptoCoinEntity)
		every { mapper.mapEntityToModel(someCryptoCoinEntity) } returns listOf(secondCoin, firstCoin)
	}

	@Test
	operator fun invoke() {

		//Act
		val actualResult = useCase().blockingGet()

		//Assert
		verify { repository.getAvailableCryptoCoins() }
		Truth.assertThat(actualResult).isInOrder { fCoin, sCoin ->
			fCoin as CryptoCoin
			sCoin as CryptoCoin
			fCoin.id.compareTo(sCoin.id)
		}
	}
}
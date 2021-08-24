package ru.jsavings.domain.usecase.cache

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import ru.jsavings.data.repository.cache.CacheRepository

class CacheUseCaseTest {

	companion object {
		private const val SOME_VALUE = true
		private val SOME_KEY = CacheKeys.JS_CURRENT_ACCOUNT
	}

	private lateinit var repository: CacheRepository
	private lateinit var useCase: CacheUseCase

	@Before
	fun setUp() {
		repository = mockk()
		useCase = CacheUseCase(repository)
	}

	@Test
	fun put() {

		//Act
		every { repository.putValue(SOME_KEY.toString(), SOME_VALUE) } answers {}
		useCase.put(SOME_KEY, SOME_VALUE)

		//Assert
		verify { repository.putValue(SOME_KEY.toString(), SOME_VALUE) }
	}

	@Test
	fun get() {

		//Act
		every { repository.getValue(SOME_KEY.toString(), SOME_VALUE) } returns SOME_VALUE
		useCase.get(SOME_KEY, SOME_VALUE)

		//Assert
		verify { repository.getValue(SOME_KEY.toString(), SOME_VALUE) }
	}

	@Test
	fun remove() {

		//Act
		every { repository.deleteValue(SOME_KEY.toString()) } answers {}
		useCase.remove(SOME_KEY)

		//Assert
		verify { repository.deleteValue(SOME_KEY.toString()) }
	}
}
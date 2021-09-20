package ru.jsavings.data.repository.cache

import android.content.SharedPreferences
import io.mockk.*
import org.junit.Assert.assertThrows

import org.junit.Before
import org.junit.Test
import java.lang.IllegalArgumentException

class CacheRepositoryTest {

	private lateinit var cacheRepository: CacheRepository
	private lateinit var sharedPreferences: SharedPreferences
	private lateinit var editor: SharedPreferences.Editor

	companion object {
		private const val KEY = "some key"
		private const val BOOLEAN = false
		private const val STRING = "some string"
		private const val INT = 0
		private const val LONG = 1L
		private const val FLOAT = 2f
		private const val ERROR_TYPE = 3.0
	}

	@Before
	fun setUp() {
		sharedPreferences = mockk()
		editor = mockk()
		cacheRepository = CacheRepository(sharedPreferences)

		every { sharedPreferences.edit() } returns editor
		every { editor.apply() } just Runs
	}

	@Test
	fun putBoolean() {

		//Act
		every { editor.putBoolean(KEY, BOOLEAN) } returns editor
		cacheRepository.putValue(KEY, BOOLEAN)

		//Assert
		verify { editor.putBoolean(KEY, BOOLEAN) }
	}

	@Test
	fun putInt() {

		//Act
		every { editor.putInt(KEY, INT) } returns editor
		cacheRepository.putValue(KEY, INT)

		//Assert
		verify { editor.putInt(KEY, INT) }
	}

	@Test
	fun putString() {

		//Act
		every { editor.putString(KEY, STRING) } returns editor
		cacheRepository.putValue(KEY, STRING)

		//Assert
		verify { editor.putString(KEY, STRING) }
	}

	@Test
	fun putLong() {

		//Act
		every { editor.putLong(KEY, LONG) } returns editor
		cacheRepository.putValue(KEY, LONG)

		//Assert
		verify { editor.putLong(KEY, LONG) }
	}

	@Test
	fun putFloat() {

		//Act
		every { editor.putFloat(KEY, FLOAT) } returns editor
		cacheRepository.putValue(KEY, FLOAT)

		//Assert
		verify { editor.putFloat(KEY, FLOAT) }
	}

	@Test
	fun putErrorType() {

		//Assert
		assertThrows(IllegalArgumentException::class.java) { cacheRepository.putValue(KEY, ERROR_TYPE) }
	}

	@Test
	fun getBoolean() {

		//Act
		every { sharedPreferences.getBoolean(KEY, BOOLEAN) } returns BOOLEAN
		cacheRepository.getValue(KEY, BOOLEAN)

		//Assert
		verify { sharedPreferences.getBoolean(KEY, BOOLEAN) }
	}

	@Test
	fun getInt() {

		//Act
		every { sharedPreferences.getInt(KEY, INT) } returns INT
		cacheRepository.getValue(KEY, INT)

		//Assert
		verify { sharedPreferences.getInt(KEY, INT) }
	}

	@Test
	fun getString() {

		//Act
		every { sharedPreferences.getString(KEY, STRING) } returns STRING
		cacheRepository.getValue(KEY, STRING)

		//Assert
		verify { sharedPreferences.getString(KEY, STRING) }
	}

	@Test
	fun getLong() {

		//Act
		every { sharedPreferences.getLong(KEY, LONG) } returns LONG
		cacheRepository.getValue(KEY, LONG)

		//Assert
		verify { sharedPreferences.getLong(KEY, LONG) }
	}

	@Test
	fun getFloat() {

		//Act
		every { sharedPreferences.getFloat(KEY, FLOAT) } returns FLOAT
		cacheRepository.getValue(KEY, FLOAT)

		//Assert
		verify { sharedPreferences.getFloat(KEY, FLOAT) }
	}

	@Test
	fun getErrorType() {

		//Assert
		assertThrows(IllegalArgumentException::class.java) { cacheRepository.getValue(KEY, ERROR_TYPE) }
	}

	@Test
	fun deleteValue() {

		//Act
		every { editor.remove(KEY) } returns editor
		cacheRepository.deleteValue(KEY)

		//Assert
		verify { editor.remove(KEY) }
	}
}
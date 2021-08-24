package ru.jsavings.domain.usecase.cache

import ru.jsavings.data.repository.cache.CacheRepository
import ru.jsavings.domain.usecase.common.BaseUseCase

/**
 * Use case to interact with app's cache
 * @param repository [CacheRepository] to interact with
 *
 * @author JustSpace
 */
class CacheUseCase(private val repository: CacheRepository) : BaseUseCase {

	/**
	 * Put value to cache
	 * @param key Key to associate value in cache with
	 * @param value Value to put
	 *
	 * @author JustSpace
	 */
	fun <T: Any> put(key: CacheKeys, value: T) = repository.putValue(key.toString(), value)

	/**
	 * Get value from cache
	 * @param key Key to which value is associating in cache with
	 * @param defaultValue Default value to return if there is no value in cache with [key]
	 * @return Value of type [T] from cache or default value if there is no value in cache with [key]
	 *
	 * @author JustSpace
	 */
	fun <T: Any> get(key: CacheKeys, defaultValue: T): T = repository.getValue(key.toString(), defaultValue)

	/**
	 * Delete value from cache
	 * @param key Key with which value is associating in cache
	 *
	 * @author JustSpace
	 */
	fun remove(key: CacheKeys) = repository.deleteValue(key.toString())
}
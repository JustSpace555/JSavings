package ru.jsavings.domain.usecase.cache

import ru.jsavings.data.repository.cache.CacheKeys
import ru.jsavings.data.repository.cache.CacheRepository
import ru.jsavings.domain.usecase.common.BaseUseCase

class CacheUseCase(val cacheRepository: CacheRepository) : BaseUseCase() {

	fun <T: Any> put(key: CacheKeys, value: T) = cacheRepository.putValue(key, value)

	inline fun <reified T: Any> get(key: CacheKeys, defaultValue: T) =
		cacheRepository.getValue(key, defaultValue)

	fun remove(key: CacheKeys) = cacheRepository.deleteValue(key)
}
package ru.jsavings.domain.usecase.cache

import ru.jsavings.data.repository.cache.CacheKeys
import ru.jsavings.data.repository.cache.CacheRepository
import ru.jsavings.domain.usecase.common.BaseUseCase

class CacheUseCase(override val repository: CacheRepository) : BaseUseCase() {

	fun <T: Any> put(key: CacheKeys, value: T) = repository.putValue(key, value)

	//TODO подумать как убрать
	inline fun <reified T: Any> get(key: CacheKeys, defaultValue: T) =
		repository.getValue(key, defaultValue)

	fun remove(key: CacheKeys) = repository.deleteValue(key)
}
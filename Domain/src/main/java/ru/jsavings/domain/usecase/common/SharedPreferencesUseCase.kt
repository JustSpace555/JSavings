package ru.jsavings.domain.usecase.common

import ru.jsavings.data.repository.sharedpreferences.common.SharedPreferencesRepository
import kotlin.reflect.KClass

abstract class SharedPreferencesUseCase(
	private val sharedPreferencesRepository: SharedPreferencesRepository
) : BaseUseCase() {

	fun <T> putValue(key: String, value: T) = sharedPreferencesRepository.putValue(key, value)
	fun <T : Any> getValue(key: String, kClass: KClass<T>, defaultValue: T) =
		sharedPreferencesRepository.getValue(key, kClass, defaultValue)
	fun removeValue(key: String) = sharedPreferencesRepository.removeValue(key)
}
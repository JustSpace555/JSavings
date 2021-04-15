package ru.jsavings.domain.usecase.sharedpreferences

import ru.jsavings.data.repository.sharedpreferences.NewAccountSharedPreferencesRepository
import ru.jsavings.domain.usecase.common.BaseUseCase
import kotlin.reflect.KClass

class NewAccountSharedPreferencesUseCase(
	private val newAccountSharedPreferencesRepository: NewAccountSharedPreferencesRepository
) : BaseUseCase() {

	fun <T> putValue(key: String, value: T) = newAccountSharedPreferencesRepository.putValue(key, value)
	fun <T : Any> getValue(key: String, kClass: KClass<T>, defaultValue: T) =
		newAccountSharedPreferencesRepository.getValue(key, kClass, defaultValue)
	fun removeValue(key: String) = newAccountSharedPreferencesRepository.removeValue(key)
}
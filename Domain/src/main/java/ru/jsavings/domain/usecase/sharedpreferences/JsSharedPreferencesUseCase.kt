package ru.jsavings.domain.usecase.sharedpreferences

import ru.jsavings.data.repository.sharedpreferences.JsSharedPreferencesRepository
import ru.jsavings.domain.usecase.common.BaseUseCase
import kotlin.reflect.KClass

class JsSharedPreferencesUseCase(
	private val jsSharedPreferencesRepository: JsSharedPreferencesRepository
) : BaseUseCase() {

	fun <T> putValue(key: String, value: T) = jsSharedPreferencesRepository.putValue(key, value)
	fun <T : Any> getValue(key: String, kClass: KClass<T>, defaultValue: T) =
		jsSharedPreferencesRepository.getValue(key, kClass, defaultValue)
}
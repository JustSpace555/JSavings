package ru.jsavings.presentation.ui.fragments.common

import androidx.lifecycle.ViewModel
import ru.jsavings.data.repository.sharedpreferences.SharedPreferencesConsts
import ru.jsavings.domain.usecase.common.BaseUseCase
import ru.jsavings.domain.usecase.common.SharedPreferencesUseCase
import ru.jsavings.domain.usecase.sharedpreferences.JsSharedPreferencesUseCase
import ru.jsavings.domain.usecase.sharedpreferences.NewAccountSharedPreferencesUseCase

abstract class BaseViewModel (
	private val disposableUseCases: List<BaseUseCase> = emptyList(),
	private val sharedPreferencesUseCases: List<SharedPreferencesUseCase> = emptyList()
) : ViewModel() {

	private fun findSharedPreferencesUseCase(sp: SharedPreferencesConsts) = when(sp) {
		is SharedPreferencesConsts.JsGlobalSP ->
			sharedPreferencesUseCases.filterIsInstance<JsSharedPreferencesUseCase>().first()
		is SharedPreferencesConsts.NewAccountSP ->
			sharedPreferencesUseCases.filterIsInstance<NewAccountSharedPreferencesUseCase>().first()
	}

	internal fun <T> putToSharedPreferences(sp: SharedPreferencesConsts, key: String, value: T) {
		val useCase = findSharedPreferencesUseCase(sp)
		useCase.putValue(key, value)
	}

	internal inline fun <reified T : Any> getFromSharedPreferences(
		sp: SharedPreferencesConsts,
		key: String,
		defaultValue: T
	): T {
		val useCase = findSharedPreferencesUseCase(sp)
		return useCase.getValue(key, T::class, defaultValue)
	}

	internal fun removeFromSharedPreferences(sp: SharedPreferencesConsts, key: String) {
		val useCase = findSharedPreferencesUseCase(sp)
		useCase.removeValue(key)
	}

	override fun onCleared() {
		super.onCleared()
		disposableUseCases.onEach { it.dispose() }
	}
}
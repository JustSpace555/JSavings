package ru.jsavings.presentation.ui.fragments.account.newaccount.balance

import ru.jsavings.domain.usecase.sharedpreferences.NewAccountSharedPreferencesUseCase
import ru.jsavings.presentation.ui.fragments.common.BaseViewModel
import kotlin.reflect.KClass

class StartingBalanceViewModel(
	private val newAccountSharedPreferencesUseCase: NewAccountSharedPreferencesUseCase
) : BaseViewModel() {

	fun <T> putItemToSharedPreferences(key: String, value: T) = newAccountSharedPreferencesUseCase.putValue(key, value)
	fun <T : Any> getFromSharedPreferences(key: String, kClass: KClass<T>, defaultValue: T) =
		newAccountSharedPreferencesUseCase.getValue(key, kClass, defaultValue)
}
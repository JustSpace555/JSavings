package ru.jsavings.presentation.ui.fragments.purses.newpurse

import ru.jsavings.domain.usecase.account.AddAccountUseCase
import ru.jsavings.domain.usecase.purse.AddPurseUseCase
import ru.jsavings.domain.usecase.sharedpreferences.JsSharedPreferencesUseCase
import ru.jsavings.domain.usecase.sharedpreferences.NewAccountSharedPreferencesUseCase
import ru.jsavings.presentation.ui.fragments.common.BaseViewModel

class NewPurseViewModel(
	private val addAccountUseCase: AddAccountUseCase,
	private val addPurseUseCase: AddPurseUseCase,
	jsSharedPreferencesUseCase: JsSharedPreferencesUseCase,
	newAccountSharedPreferencesUseCase: NewAccountSharedPreferencesUseCase
) : BaseViewModel(
	disposableUseCases = listOf(addAccountUseCase, addPurseUseCase),
	sharedPreferencesUseCases = listOf(jsSharedPreferencesUseCase, newAccountSharedPreferencesUseCase)
) {
}
package ru.jsavings.presentation.ui.fragments.purses.newpurse

import ru.jsavings.domain.usecase.database.account.AddAccountUseCase
import ru.jsavings.domain.usecase.database.purse.AddPurseUseCase
import ru.jsavings.domain.usecase.network.GetAllCoinsUseCase
import ru.jsavings.domain.usecase.sharedpreferences.JsSharedPreferencesUseCase
import ru.jsavings.domain.usecase.sharedpreferences.NewAccountSharedPreferencesUseCase
import ru.jsavings.presentation.ui.fragments.common.BaseViewModel

class NewPurseViewModel(
	private val addAccountUseCase: AddAccountUseCase,
	private val addPurseUseCase: AddPurseUseCase,
	private val getAllCoinsUseCase: GetAllCoinsUseCase,
	jsSharedPreferencesUseCase: JsSharedPreferencesUseCase,
	newAccountSharedPreferencesUseCase: NewAccountSharedPreferencesUseCase
) : BaseViewModel(
	disposableUseCases = listOf(addAccountUseCase, addPurseUseCase, getAllCoinsUseCase),
	sharedPreferencesUseCases = listOf(jsSharedPreferencesUseCase, newAccountSharedPreferencesUseCase)
) {
}
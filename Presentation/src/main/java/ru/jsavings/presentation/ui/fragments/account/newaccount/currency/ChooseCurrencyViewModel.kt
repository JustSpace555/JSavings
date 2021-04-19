package ru.jsavings.presentation.ui.fragments.account.newaccount.currency

import ru.jsavings.domain.usecase.sharedpreferences.NewAccountSharedPreferencesUseCase
import ru.jsavings.presentation.ui.fragments.common.BaseViewModel

class ChooseCurrencyViewModel(
	newAccountSharedPreferencesUseCase: NewAccountSharedPreferencesUseCase
) : BaseViewModel(
	sharedPreferencesUseCases = listOf(newAccountSharedPreferencesUseCase)
)
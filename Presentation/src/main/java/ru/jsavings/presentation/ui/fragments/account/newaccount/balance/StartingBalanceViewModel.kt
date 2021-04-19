package ru.jsavings.presentation.ui.fragments.account.newaccount.balance

import ru.jsavings.domain.usecase.sharedpreferences.NewAccountSharedPreferencesUseCase
import ru.jsavings.presentation.ui.fragments.common.BaseViewModel

class StartingBalanceViewModel(
	newAccountSharedPreferencesUseCase: NewAccountSharedPreferencesUseCase
) : BaseViewModel(
	sharedPreferencesUseCases = listOf(newAccountSharedPreferencesUseCase)
)
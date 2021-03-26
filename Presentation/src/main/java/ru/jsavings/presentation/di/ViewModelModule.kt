package ru.jsavings.presentation.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.jsavings.presentation.ui.fragments.newpurse.AddNewAccountNameViewModel

val viewModelModule = module {
	viewModel { AddNewAccountNameViewModel() }
}
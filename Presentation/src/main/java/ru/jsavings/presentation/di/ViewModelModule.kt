package ru.jsavings.presentation.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.jsavings.presentation.ui.fragments.account.newaccount.balance.StartingBalanceViewModel
import ru.jsavings.presentation.ui.fragments.account.newaccount.currency.ChooseCurrencyViewModel
import ru.jsavings.presentation.ui.fragments.account.newaccount.firstpurse.CreateFirstPurseViewModel
import ru.jsavings.presentation.ui.fragments.account.newaccount.name.AddNameViewModel
import ru.jsavings.presentation.ui.fragments.account.newaccount.ready.ReadyViewModel
import ru.jsavings.presentation.ui.fragments.calendar.CalendarViewModel
import ru.jsavings.presentation.ui.fragments.graph.GraphViewModel
import ru.jsavings.presentation.ui.fragments.intro.IntroViewModel
import ru.jsavings.presentation.ui.fragments.purses.allpurses.PursesViewModel
import ru.jsavings.presentation.ui.fragments.purses.newpurse.NewPurseViewModel

internal val viewModelModule = module {
	//Into
	viewModel { IntroViewModel(get(), get(), get()) }
	viewModel { ReadyViewModel() }

	//New account
	viewModel { AddNameViewModel(get(), get()) }
	viewModel { ChooseCurrencyViewModel(get()) }
	viewModel { StartingBalanceViewModel(get()) }
	viewModel { CreateFirstPurseViewModel() }

	//Calendar
	viewModel { CalendarViewModel() }

	//Graph
	viewModel { GraphViewModel() }

	//Purses
	viewModel { PursesViewModel() }
	viewModel { NewPurseViewModel() }
}
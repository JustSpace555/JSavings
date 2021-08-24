package ru.jsavings.presentation.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.jsavings.presentation.ui.fragments.account.newaccount.currency.ChooseCurrencyViewModel
import ru.jsavings.presentation.ui.fragments.account.newaccount.name.AddNewAccountNameViewModel
import ru.jsavings.presentation.ui.fragments.calendar.CalendarViewModel
import ru.jsavings.presentation.ui.fragments.graph.GraphViewModel
import ru.jsavings.presentation.ui.fragments.intro.IntroViewModel
import ru.jsavings.presentation.ui.fragments.transactions.TransactionsViewModel
import ru.jsavings.presentation.ui.fragments.wallet.allwallets.WalletsViewModel
import ru.jsavings.presentation.ui.fragments.wallet.newwallet.NewWalletViewModel

/**
 * Koin module of all ViewModels in [ru.jsavings.presentation] module
 * 
 * @author JustSpace
 */
internal val viewModelModule = module {
	//Into
	viewModel { IntroViewModel(get(), get()) }

	//New account
	viewModel { AddNewAccountNameViewModel(get()) }
	viewModel { ChooseCurrencyViewModel(get(), get(), get()) }

	//Transaction
	viewModel { TransactionsViewModel(get(), get()) }

	//Calendar
	viewModel { CalendarViewModel() }

	//Graph
	viewModel { GraphViewModel() }

	//Wallets
	viewModel { WalletsViewModel() }
	viewModel { NewWalletViewModel(get(), get(), get()) }
}
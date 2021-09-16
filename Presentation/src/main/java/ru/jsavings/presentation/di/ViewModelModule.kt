package ru.jsavings.presentation.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.jsavings.presentation.ui.fragments.categories.categorieslist.viewpageritem.ItemCategoryListViewModel
import ru.jsavings.presentation.viewmodels.MainSharedViewModel
import ru.jsavings.presentation.viewmodels.account.newaccount.AddNewAccountNameViewModel
import ru.jsavings.presentation.viewmodels.account.newaccount.ChooseCurrencyViewModel
import ru.jsavings.presentation.viewmodels.categories.AddNewCategoryViewModel
import ru.jsavings.presentation.viewmodels.categories.CategoriesListViewModel
import ru.jsavings.presentation.viewmodels.transactions.NewTransactionViewModel
import ru.jsavings.presentation.viewmodels.transactions.TransactionInfoViewModel
import ru.jsavings.presentation.viewmodels.wallets.NewWalletViewModel

/**
 * Koin module of all ViewModels in [ru.jsavings.presentation] module
 * 
 * @author JustSpace
 */
internal val viewModelModule = module {

	//Main
	viewModel { MainSharedViewModel(get(), get()) }

	//New account
	viewModel { AddNewAccountNameViewModel(get()) }
	viewModel { ChooseCurrencyViewModel(get(), get(), get()) }

	//Transaction
	viewModel { NewTransactionViewModel(get()) }
	viewModel { TransactionInfoViewModel(get(), get()) }

	//Transaction categories
	viewModel { CategoriesListViewModel(get()) }
	viewModel { AddNewCategoryViewModel(get()) }
	viewModel { ItemCategoryListViewModel(get()) }

	//Wallets
	viewModel { NewWalletViewModel(get(), get()) }
}
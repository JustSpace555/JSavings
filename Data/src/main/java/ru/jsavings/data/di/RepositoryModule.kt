package ru.jsavings.data.di

import android.content.Context
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.jsavings.data.repository.account.AccountRepository
import ru.jsavings.data.repository.account.AccountRepositoryImpl
import ru.jsavings.data.repository.binding.AccountWithPursesRepository
import ru.jsavings.data.repository.binding.AccountWithPursesRepositoryImpl
import ru.jsavings.data.repository.transaction.caterory.TransactionCategoryRepository
import ru.jsavings.data.repository.transaction.caterory.TransactionCategoryRepositoryImpl
import ru.jsavings.data.repository.purse.PurseRepository
import ru.jsavings.data.repository.purse.PurseRepositoryImpl
import ru.jsavings.data.repository.sharedpreferences.*
import ru.jsavings.data.repository.transaction.TransactionRepository
import ru.jsavings.data.repository.transaction.TransactionRepositoryImpl

internal val repositoryModule = module {

	single<JsSharedPreferencesRepository> { SharedPreferencesRepositoryImpl(
		androidContext().getSharedPreferences(JsSharedPreferences.FILE_NAME, Context.MODE_PRIVATE)
	)}
	single<NewAccountSharedPreferencesRepository> { SharedPreferencesRepositoryImpl(
		androidContext().getSharedPreferences(NewAccountSharedPreferences.FILE_NAME, Context.MODE_PRIVATE)
	)}

	single<AccountRepository> { AccountRepositoryImpl(get(), get()) }
	single<TransactionCategoryRepository> { TransactionCategoryRepositoryImpl(get(), get()) }
	single<PurseRepository> { PurseRepositoryImpl(get(), get()) }
	single<TransactionRepository> { TransactionRepositoryImpl(get(), get(), get()) }

	single<AccountWithPursesRepository> { AccountWithPursesRepositoryImpl(get(), get()) }
}
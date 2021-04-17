package ru.jsavings.data.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.jsavings.data.repository.account.AccountRepository
import ru.jsavings.data.repository.account.AccountRepositoryImpl
import ru.jsavings.data.repository.binding.AccountWithPursesRepository
import ru.jsavings.data.repository.binding.AccountWithPursesRepositoryImpl
import ru.jsavings.data.repository.purse.PurseRepository
import ru.jsavings.data.repository.purse.PurseRepositoryImpl
import ru.jsavings.data.repository.sharedpreferences.*
import ru.jsavings.data.repository.transaction.TransactionRepository
import ru.jsavings.data.repository.transaction.TransactionRepositoryImpl
import ru.jsavings.data.repository.transaction.caterory.TransactionCategoryRepository
import ru.jsavings.data.repository.transaction.caterory.TransactionCategoryRepositoryImpl

internal val repositoryModule = module {

	single<JsSharedPreferencesRepository> {
		SharedPreferencesRepositoryImpl(get(named(JsSharedPreferences::class.java.simpleName)))
	}
	single<NewAccountSharedPreferencesRepository> {
		SharedPreferencesRepositoryImpl(get(named(NewAccountSharedPreferences::class.java.simpleName)))
	}

	single<AccountRepository> { AccountRepositoryImpl(get(), get()) }
	single<TransactionCategoryRepository> { TransactionCategoryRepositoryImpl(get(), get()) }
	single<PurseRepository> { PurseRepositoryImpl(get(), get()) }
	single<TransactionRepository> { TransactionRepositoryImpl(get(), get(), get()) }

	single<AccountWithPursesRepository> { AccountWithPursesRepositoryImpl(get(), get()) }
}
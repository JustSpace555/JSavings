package ru.jsavings.data.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.jsavings.data.repository.database.account.AccountRepository
import ru.jsavings.data.repository.database.account.AccountRepositoryImpl
import ru.jsavings.data.repository.database.binding.AccountWithPursesRepository
import ru.jsavings.data.repository.database.binding.AccountWithPursesRepositoryImpl
import ru.jsavings.data.repository.database.purse.PurseRepository
import ru.jsavings.data.repository.database.purse.PurseRepositoryImpl
import ru.jsavings.data.repository.database.transaction.TransactionRepository
import ru.jsavings.data.repository.database.transaction.TransactionRepositoryImpl
import ru.jsavings.data.repository.database.transaction.caterory.TransactionCategoryRepository
import ru.jsavings.data.repository.database.transaction.caterory.TransactionCategoryRepositoryImpl
import ru.jsavings.data.repository.network.crypto.CryptoRepository
import ru.jsavings.data.repository.network.crypto.CryptoRepositoryImpl
import ru.jsavings.data.repository.sharedpreferences.JsSharedPreferencesRepository
import ru.jsavings.data.repository.sharedpreferences.NewAccountSharedPreferencesRepository
import ru.jsavings.data.repository.sharedpreferences.SharedPreferencesConsts
import ru.jsavings.data.repository.sharedpreferences.SharedPreferencesRepositoryImpl

internal val repositoryModule = module {

	//Shared preferences
	single<JsSharedPreferencesRepository> {
		SharedPreferencesRepositoryImpl(get(named(SharedPreferencesConsts.JsGlobalSP::class.java.simpleName)))
	}
	single<NewAccountSharedPreferencesRepository> {
		SharedPreferencesRepositoryImpl(get(named(SharedPreferencesConsts.NewAccountSP::class.java.simpleName)))
	}

	//DataBase
	single<AccountRepository> { AccountRepositoryImpl(get(), get()) }
	single<TransactionCategoryRepository> { TransactionCategoryRepositoryImpl(get(), get()) }
	single<PurseRepository> { PurseRepositoryImpl(get(), get()) }
	single<TransactionRepository> { TransactionRepositoryImpl(get(), get()) }

	//DataBase binding
	single<AccountWithPursesRepository> { AccountWithPursesRepositoryImpl(get(), get()) }

	//Network
	single<CryptoRepository> { CryptoRepositoryImpl(get(), get()) }
}
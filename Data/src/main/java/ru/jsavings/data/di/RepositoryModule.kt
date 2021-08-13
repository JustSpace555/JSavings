package ru.jsavings.data.di

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
<<<<<<< refs/remotes/origin/dev
import ru.jsavings.data.repository.cache.CacheRepository
import ru.jsavings.data.repository.database.account.AccountRepository
import ru.jsavings.data.repository.database.account.AccountRepositoryImpl
import ru.jsavings.data.repository.database.purse.PurseRepository
import ru.jsavings.data.repository.database.purse.PurseRepositoryImpl
import ru.jsavings.data.repository.database.transaction.TransactionRepository
import ru.jsavings.data.repository.database.transaction.TransactionRepositoryImpl
import ru.jsavings.data.repository.database.transaction.caterory.TransactionCategoryRepository
import ru.jsavings.data.repository.database.transaction.caterory.TransactionCategoryRepositoryImpl
import ru.jsavings.data.repository.network.crypto.CryptoRepository
import ru.jsavings.data.repository.network.crypto.CryptoRepositoryImpl
import ru.jsavings.data.repository.network.currency.CurrencyRepository
import ru.jsavings.data.repository.network.currency.CurrencyRepositoryImpl
=======
import ru.jsavings.data.repository.account.AccountRepository
import ru.jsavings.data.repository.account.AccountRepositoryImpl
import ru.jsavings.data.repository.binding.AccountWithPursesRepository
import ru.jsavings.data.repository.binding.AccountWithPursesRepositoryImpl
import ru.jsavings.data.repository.purse.PurseRepository
import ru.jsavings.data.repository.purse.PurseRepositoryImpl
import ru.jsavings.data.repository.sharedpreferences.SharedPreferencesRepositoryImpl
import ru.jsavings.data.repository.transaction.TransactionRepository
import ru.jsavings.data.repository.transaction.TransactionRepositoryImpl
import ru.jsavings.data.repository.transaction.caterory.TransactionCategoryRepository
import ru.jsavings.data.repository.transaction.caterory.TransactionCategoryRepositoryImpl
>>>>>>> Rework started

internal val repositoryModule = module {

	//DataBase
	single<AccountRepository> { AccountRepositoryImpl(get(), get()) }
	single<TransactionCategoryRepository> { TransactionCategoryRepositoryImpl(get(), get()) }
	single<PurseRepository> { PurseRepositoryImpl(get(), get()) }
	single<TransactionRepository> { TransactionRepositoryImpl(get(), get()) }

	//Cache
	single { CacheRepository(
		androidContext().getSharedPreferences("JS_CACHE", Context.MODE_PRIVATE)
	) }

	//Network
	single<CurrencyRepository> { CurrencyRepositoryImpl(get(), get(), get()) }
	single<CryptoRepository> { CryptoRepositoryImpl(get(), get()) }
}
package ru.jsavings.data.di

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
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
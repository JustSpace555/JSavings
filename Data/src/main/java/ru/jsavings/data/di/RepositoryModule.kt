package ru.jsavings.data.di

import org.koin.dsl.module
import ru.jsavings.data.repository.account.AccountRepository
import ru.jsavings.data.repository.account.AccountRepositoryImpl
import ru.jsavings.data.repository.transaction.caterory.TransactionCategoryRepository
import ru.jsavings.data.repository.transaction.caterory.TransactionCategoryRepositoryImpl
import ru.jsavings.data.repository.purse.PurseRepository
import ru.jsavings.data.repository.purse.PurseRepositoryImpl
import ru.jsavings.data.repository.transaction.TransactionRepository
import ru.jsavings.data.repository.transaction.TransactionRepositoryImpl

internal val repositoryModule = module {

	single<AccountRepository> { AccountRepositoryImpl(get(), get()) }

	single<TransactionCategoryRepository> { TransactionCategoryRepositoryImpl(get(), get()) }

	single<PurseRepository> { PurseRepositoryImpl(get(), get()) }

	single<TransactionRepository> { TransactionRepositoryImpl(get(), get()) }
}
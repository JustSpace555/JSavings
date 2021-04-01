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

val repositoryModule = module {

	single<AccountRepository> { AccountRepositoryImpl(get()) }

	single<TransactionCategoryRepository> { TransactionCategoryRepositoryImpl(get()) }

	single<PurseRepository> { PurseRepositoryImpl(get()) }

	single<TransactionRepository> { TransactionRepositoryImpl(get()) }
}
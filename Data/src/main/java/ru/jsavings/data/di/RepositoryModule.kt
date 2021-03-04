package ru.jsavings.data.di

import org.koin.dsl.module
import ru.jsavings.data.model.AccountRepository
import ru.jsavings.data.implementations.AccountRepositoryImpl
import ru.jsavings.data.model.CategoryRepository
import ru.jsavings.data.implementations.CategoryRepositoryImpl
import ru.jsavings.data.model.PurseRepository
import ru.jsavings.data.implementations.PurseRepositoryImpl
import ru.jsavings.data.model.TransactionRepository
import ru.jsavings.data.implementations.TransactionRepositoryImpl

val repositoryModule = module {

	single<AccountRepository> { AccountRepositoryImpl(get()) }

	single<CategoryRepository> { CategoryRepositoryImpl(get()) }

	single<PurseRepository> { PurseRepositoryImpl(get()) }

	single<TransactionRepository> { TransactionRepositoryImpl(get()) }
}
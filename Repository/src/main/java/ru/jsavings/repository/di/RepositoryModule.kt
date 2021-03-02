package ru.jsavings.repository.di

import org.koin.dsl.module
import ru.jsavings.repository.entity.account.AccountRepository
import ru.jsavings.repository.entity.account.AccountRepositoryImpl
import ru.jsavings.repository.entity.category.CategoryRepository
import ru.jsavings.repository.entity.category.CategoryRepositoryImpl
import ru.jsavings.repository.entity.purse.PurseRepository
import ru.jsavings.repository.entity.purse.PurseRepositoryImpl
import ru.jsavings.repository.entity.transaction.TransactionRepository
import ru.jsavings.repository.entity.transaction.TransactionRepositoryImpl

val repositoryModule = module {

	single<AccountRepository> { AccountRepositoryImpl(get()) }

	single<CategoryRepository> { CategoryRepositoryImpl(get()) }

	single<PurseRepository> { PurseRepositoryImpl(get()) }

	single<TransactionRepository> { TransactionRepositoryImpl(get()) }
}
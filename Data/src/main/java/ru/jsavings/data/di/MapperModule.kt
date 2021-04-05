package ru.jsavings.data.di

import org.koin.dsl.module
import ru.jsavings.data.mappers.AccountMapper
import ru.jsavings.data.mappers.PurseMapper
import ru.jsavings.data.mappers.transaction.TransactionCategoryMapper
import ru.jsavings.data.mappers.transaction.TransactionMapper

internal val mapperModule = module {

	single { TransactionCategoryMapper() }
	single { TransactionMapper() }
	single { PurseMapper() }
	single { AccountMapper() }
}
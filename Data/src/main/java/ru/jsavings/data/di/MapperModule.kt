package ru.jsavings.data.di

import org.koin.dsl.module
import ru.jsavings.domain.usecase.mappers.AccountMapper
import ru.jsavings.domain.usecase.mappers.PurseMapper
import ru.jsavings.domain.usecase.mappers.binding.AccountWithPursesMapper
import ru.jsavings.domain.usecase.mappers.transaction.TransactionCategoryMapper
import ru.jsavings.domain.usecase.mappers.transaction.TransactionMapper

internal val mapperModule = module {

	single { ru.jsavings.domain.usecase.mappers.transaction.TransactionCategoryMapper() }
	single { ru.jsavings.domain.usecase.mappers.transaction.TransactionMapper(get()) }
	single { ru.jsavings.domain.usecase.mappers.PurseMapper(get()) }
	single { ru.jsavings.domain.usecase.mappers.AccountMapper() }

	single { ru.jsavings.domain.usecase.mappers.binding.AccountWithPursesMapper(get(), get()) }
}
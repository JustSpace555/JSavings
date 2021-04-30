package ru.jsavings.data.di

import org.koin.dsl.module
import ru.jsavings.data.mappers.database.AccountMapper
import ru.jsavings.data.mappers.database.PurseMapper
import ru.jsavings.data.mappers.database.binding.AccountWithPursesMapper
import ru.jsavings.data.mappers.database.transaction.TransactionCategoryMapper
import ru.jsavings.data.mappers.database.transaction.TransactionMapper
import ru.jsavings.data.mappers.network.CryptoCoinMapper

internal val mapperModule = module {

	//DataBase
	single { TransactionCategoryMapper() }
	single { TransactionMapper(get()) }
	single { PurseMapper(get()) }
	single { AccountMapper() }

	//DataBaseBinding
	single { AccountWithPursesMapper(get(), get()) }

	//Network
	single { CryptoCoinMapper() }
}
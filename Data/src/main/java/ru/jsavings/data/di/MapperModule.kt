package ru.jsavings.data.di

import org.koin.dsl.module
import ru.jsavings.data.mappers.database.AccountMapper
import ru.jsavings.data.mappers.database.PurseMapper
import ru.jsavings.data.mappers.database.transaction.TransactionCategoryMapper
import ru.jsavings.data.mappers.database.transaction.TransactionMapper
import ru.jsavings.data.mappers.network.ConversionMapper
import ru.jsavings.data.mappers.network.CryptoCoinsRequestMapper
import ru.jsavings.data.mappers.network.CurrencyRequestMapper

internal val mapperModule = module {

	//DataBase
	factory { TransactionCategoryMapper() }
	factory { TransactionMapper(get()) }
	factory { PurseMapper(get()) }
	factory { AccountMapper() }

	//Network
	factory { CurrencyRequestMapper() }
	factory { CryptoCoinsRequestMapper() }
	factory { ConversionMapper() }
}
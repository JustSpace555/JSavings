package ru.jsavings.data.di

import org.koin.dsl.module
<<<<<<< refs/remotes/origin/dev
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
=======
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
>>>>>>> Rework started
}
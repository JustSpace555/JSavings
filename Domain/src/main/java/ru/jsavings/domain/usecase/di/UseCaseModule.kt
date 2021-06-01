package ru.jsavings.domain.usecase.di

import org.koin.dsl.module
import ru.jsavings.domain.usecase.cache.CacheUseCase
import ru.jsavings.domain.usecase.database.account.AddAccountUseCase
import ru.jsavings.domain.usecase.database.account.DeleteAccountsUseCase
import ru.jsavings.domain.usecase.database.account.GetAllAccountsUseCase
import ru.jsavings.domain.usecase.database.account.GetAllAccountsWithPursesUseCase
import ru.jsavings.domain.usecase.database.purse.AddPurseUseCase
import ru.jsavings.domain.usecase.network.GetAllCoinsUseCase

internal val useCaseModule = module {

	//Account
	factory { GetAllAccountsWithPursesUseCase(get()) }
	factory { DeleteAccountsUseCase(get()) }
	factory { GetAllAccountsUseCase(get()) }
	factory { AddAccountUseCase(get()) }

	//Coins
	factory { GetAllCoinsUseCase(get()) }

	//Purse
	factory { AddPurseUseCase(get()) }

	//Cache
	factory { CacheUseCase(get()) }
}
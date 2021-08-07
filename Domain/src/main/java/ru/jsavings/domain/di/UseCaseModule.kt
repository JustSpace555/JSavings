package ru.jsavings.domain.di

import org.koin.dsl.module
import ru.jsavings.domain.usecase.cache.CacheUseCase
import ru.jsavings.domain.usecase.database.account.*
import ru.jsavings.domain.usecase.database.purse.AddPurseUseCase
import ru.jsavings.domain.usecase.network.ConvertCurrencyUseCase
import ru.jsavings.domain.usecase.network.GetCryptoCoinsUseCase
import ru.jsavings.domain.usecase.network.GetCurrenciesUseCase

internal val useCaseModule = module {

	//Account
	factory { DeleteAccountsUseCase(get()) }
	factory { GetAccountsUseCase(get()) }
	factory { AddAccountUseCase(get()) }
	factory { UpdateAccountUseCase(get()) }
	factory { AddOrUpdateAccountAndCreateNewPurseUseCase(get(), get(), get()) }

	//Purse
	factory { AddPurseUseCase(get()) }

	//Cache
	factory { CacheUseCase(get()) }



	//Currencies
	factory { GetCurrenciesUseCase(get()) }

	//Coins
	factory { GetCryptoCoinsUseCase(get()) }

	//General
	factory { ConvertCurrencyUseCase(get()) }
}
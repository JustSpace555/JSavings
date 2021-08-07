package ru.jsavings.domain.di

import org.koin.dsl.module
import ru.jsavings.domain.interactor.database.AccountInteractor
import ru.jsavings.domain.interactor.database.DataBaseInteractor
import ru.jsavings.domain.interactor.database.PurseInteractor
import ru.jsavings.domain.interactor.network.CryptoCoinInteractor
import ru.jsavings.domain.interactor.network.CurrenciesInteractor
import ru.jsavings.domain.interactor.network.NetworkInteractor

internal val interactorModule = module {

	//DataBase
	single { AccountInteractor() }
	single { PurseInteractor() }
	single { DataBaseInteractor() }

	//Network
	single { CryptoCoinInteractor() }
	single { CurrenciesInteractor() }
	single { NetworkInteractor() }
}
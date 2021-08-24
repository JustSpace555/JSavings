package ru.jsavings.domain.di

import org.koin.dsl.module
import ru.jsavings.domain.interactor.database.AccountInteractor
import ru.jsavings.domain.interactor.database.DataBaseInteractor
import ru.jsavings.domain.interactor.database.TransactionInteractor
import ru.jsavings.domain.interactor.database.WalletInteractor
import ru.jsavings.domain.interactor.network.NetworkInteractor

/**
 * Koin module of all interactors in [ru.jsavings.domain.interactor] package
 *
 * @author JustSpace
 */
internal val interactorModule = module {

	//DataBase
	single { AccountInteractor() }
	single { DataBaseInteractor() }
	single { WalletInteractor() }
	single { TransactionInteractor() }

	//Network
	single { NetworkInteractor() }
}
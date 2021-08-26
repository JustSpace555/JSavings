package ru.jsavings.domain.di

import org.koin.dsl.module
import ru.jsavings.domain.interactor.database.*
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
	single { TransactionCategoryInteractor() }

	//Network
	single { NetworkInteractor() }
}
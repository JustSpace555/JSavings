package ru.jsavings.domain.usecase.di

import org.koin.dsl.module
import ru.jsavings.domain.usecase.database.account.AddAccountUseCase
import ru.jsavings.domain.usecase.database.account.DeleteAccountsUseCase
import ru.jsavings.domain.usecase.database.account.GetAllAccountsUseCase
import ru.jsavings.domain.usecase.database.account.GetAllAccountsWithPursesUseCase
import ru.jsavings.domain.usecase.database.purse.AddPurseUseCase
import ru.jsavings.domain.usecase.sharedpreferences.JsSharedPreferencesUseCase
import ru.jsavings.domain.usecase.sharedpreferences.NewAccountSharedPreferencesUseCase

internal val useCaseModule = module {

	//Account
	factory { GetAllAccountsWithPursesUseCase(get()) }
	factory { DeleteAccountsUseCase(get()) }
	factory { GetAllAccountsUseCase(get()) }
	factory { AddAccountUseCase(get()) }

	//Purse
	factory { AddPurseUseCase(get()) }

	//SharedPreferences
	factory { JsSharedPreferencesUseCase(get()) }
	factory { NewAccountSharedPreferencesUseCase(get()) }
}
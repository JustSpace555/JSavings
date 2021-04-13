package ru.jsavings.domain.usecase.di

import org.koin.dsl.module
import ru.jsavings.domain.usecase.account.DeleteAccountsUseCase
import ru.jsavings.domain.usecase.account.GetAllAccountsUseCase
import ru.jsavings.domain.usecase.account.GetAllAccountsWithPursesUseCase

internal val useCaseModule = module {

	//Account
	factory { GetAllAccountsWithPursesUseCase(get()) }
	factory { DeleteAccountsUseCase(get()) }
	factory { GetAllAccountsUseCase(get()) }
}
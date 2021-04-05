package ru.jsavings.domain.usecase.di

import org.koin.dsl.module
import ru.jsavings.domain.usecase.account.GetAllAccountsUseCase
import ru.jsavings.domain.usecase.account.GetAllAccountsUseCaseImpl
import ru.jsavings.domain.usecase.purse.GetPursesByAccountIdUseCase
import ru.jsavings.domain.usecase.purse.GetPursesByAccountIdUseCaseImpl

internal val useCaseModule = module {

	factory<GetAllAccountsUseCase> { GetAllAccountsUseCaseImpl(get()) }
	factory<GetPursesByAccountIdUseCase> { (accountId: Int) -> GetPursesByAccountIdUseCaseImpl(get(), accountId) }
}
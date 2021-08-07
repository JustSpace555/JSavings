package ru.jsavings.domain.interactor.database

import org.koin.java.KoinJavaComponent.inject
import ru.jsavings.domain.interactor.common.BaseInteractor
import ru.jsavings.domain.usecase.database.account.AddAccountUseCase
import ru.jsavings.domain.usecase.database.account.DeleteAccountsUseCase
import ru.jsavings.domain.usecase.database.account.GetAccountsUseCase
import ru.jsavings.domain.usecase.database.account.UpdateAccountUseCase

class AccountInteractor : BaseInteractor {

	val addAccountUseCase by inject(AddAccountUseCase::class.java)

	val deleteAccountsUseCase by inject(DeleteAccountsUseCase::class.java)

	val getAllAccountsUseCase by inject(GetAccountsUseCase::class.java)

	val updateAccountUseCase by inject(UpdateAccountUseCase::class.java)
}
package ru.jsavings.domain.interactor.database

import org.koin.java.KoinJavaComponent.inject
import ru.jsavings.domain.interactor.common.BaseInteractor
import ru.jsavings.domain.usecase.database.account.AddOrUpdateAccountAndCreateNewPurseUseCase

class DataBaseInteractor : BaseInteractor {

	val accountInteractor by inject(AccountInteractor::class.java)

	val purseInteractor by inject(PurseInteractor::class.java)

	val addOrUpdateAccountAndCreateNewPurseUseCase by inject(
		AddOrUpdateAccountAndCreateNewPurseUseCase::class.java
	)
}
package ru.jsavings.domain.interactor.database

import org.koin.java.KoinJavaComponent.inject
import ru.jsavings.domain.interactor.common.BaseInteractor
import ru.jsavings.domain.usecase.database.purse.AddPurseUseCase

class PurseInteractor : BaseInteractor {

	val addPurseUseCase by inject(AddPurseUseCase::class.java)
}
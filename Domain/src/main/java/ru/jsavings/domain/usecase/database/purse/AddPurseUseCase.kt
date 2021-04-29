package ru.jsavings.domain.usecase.database.purse

import io.reactivex.rxjava3.core.Completable
import ru.jsavings.data.model.database.purse.Purse
import ru.jsavings.data.repository.database.purse.PurseRepository
import ru.jsavings.domain.usecase.common.CompletableUseCase

class AddPurseUseCase(
	private val purseRepository: PurseRepository
) : CompletableUseCase<Purse>() {

	override fun buildCompletableUseCase(params: Purse): Completable = purseRepository.addNewPurse(params)
}
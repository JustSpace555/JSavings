package ru.jsavings.domain.usecase.purse

import io.reactivex.rxjava3.core.Completable
import ru.jsavings.data.model.purse.Purse
import ru.jsavings.data.repository.purse.PurseRepository
import ru.jsavings.domain.usecase.common.CompletableUseCase

class AddPurseUseCase(
	private val purseRepository: PurseRepository
) : CompletableUseCase<Purse>() {

	override fun buildCompletableUseCase(params: Purse): Completable = purseRepository.addNewPurse(params)
}
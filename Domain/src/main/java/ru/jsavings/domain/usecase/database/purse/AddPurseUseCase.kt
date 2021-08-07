package ru.jsavings.domain.usecase.database.purse

import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.model.database.purse.Purse
import ru.jsavings.data.repository.database.purse.PurseRepository
import ru.jsavings.domain.usecase.common.SingleUseCase

class AddPurseUseCase(
	override val repository: PurseRepository
) : SingleUseCase<Long, Purse>() {

	override fun buildSingleUseCase(params: Purse): Single<Long> = repository.addNewPurse(params)
}
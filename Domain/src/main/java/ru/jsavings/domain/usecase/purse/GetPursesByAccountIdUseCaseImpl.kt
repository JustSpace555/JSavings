package ru.jsavings.domain.usecase.purse

import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.model.purse.Purse
import ru.jsavings.data.repository.purse.PurseRepository

class GetPursesByAccountIdUseCaseImpl(
	private val purseRepository: PurseRepository,
	private val accountId: Int
) : GetPursesByAccountIdUseCase {

	override fun invoke(): Single<List<Purse>> = purseRepository.getPursesByAccountId(accountId)
}
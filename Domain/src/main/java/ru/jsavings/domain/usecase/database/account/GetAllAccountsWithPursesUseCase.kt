package ru.jsavings.domain.usecase.database.account

import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.model.database.binding.AccountWithPurses
import ru.jsavings.data.repository.database.binding.AccountWithPursesRepository
import ru.jsavings.domain.usecase.common.SingleUseCase

class GetAllAccountsWithPursesUseCase(
	private val accountWithPursesRepository: AccountWithPursesRepository
) : SingleUseCase<List<AccountWithPurses>, Unit>() {

	override fun buildSingleUseCase(params: Unit): Single<List<AccountWithPurses>> =
		accountWithPursesRepository.getAllAccountsWithPurses()
}
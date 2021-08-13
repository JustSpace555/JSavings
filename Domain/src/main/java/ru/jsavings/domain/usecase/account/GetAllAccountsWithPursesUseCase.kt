package ru.jsavings.domain.usecase.account

import io.reactivex.rxjava3.core.Single
import ru.jsavings.domain.usecase.model.Account
import ru.jsavings.domain.usecase.model.binding.AccountWithPurses
import ru.jsavings.data.repository.account.AccountRepository
import ru.jsavings.data.repository.binding.AccountWithPursesRepository
import ru.jsavings.domain.usecase.common.SingleUseCase

class GetAllAccountsWithPursesUseCase(
	private val accountWithPursesRepository: AccountWithPursesRepository
) : SingleUseCase<List<AccountWithPurses>, Unit>() {

	override fun buildSingleUseCase(params: Unit): Single<List<AccountWithPurses>> =
		accountWithPursesRepository.getAllAccountsWithPurses()
}
package ru.jsavings.domain.usecase.account

import io.reactivex.rxjava3.core.Single
import ru.jsavings.domain.usecase.model.Account
import ru.jsavings.data.repository.account.AccountRepository
import ru.jsavings.domain.usecase.common.SingleUseCase

class GetAllAccountsUseCase(
	private val accountRepository: AccountRepository
) : SingleUseCase<List<Account>, Unit>() {

	override fun buildSingleUseCase(params: Unit): Single<List<Account>> = accountRepository.getAllAccounts()
}
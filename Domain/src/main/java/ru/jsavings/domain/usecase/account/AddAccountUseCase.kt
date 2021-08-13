package ru.jsavings.domain.usecase.account

import io.reactivex.rxjava3.core.Completable
import ru.jsavings.domain.usecase.model.Account
import ru.jsavings.data.repository.account.AccountRepository
import ru.jsavings.domain.usecase.common.CompletableUseCase

class AddAccountUseCase(
	private val accountRepository: AccountRepository
) : CompletableUseCase<Account>() {

	override fun buildCompletableUseCase(params: Account): Completable = accountRepository.createNewAccount(params)
}
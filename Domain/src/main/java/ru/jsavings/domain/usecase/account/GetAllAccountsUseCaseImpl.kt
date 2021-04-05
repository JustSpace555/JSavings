package ru.jsavings.domain.usecase.account

import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.model.Account
import ru.jsavings.data.repository.account.AccountRepository

class GetAllAccountsUseCaseImpl(
	private val accountRepository: AccountRepository
) : GetAllAccountsUseCase {
	override fun invoke(): Single<List<Account>> = accountRepository.getAllAccounts()
}
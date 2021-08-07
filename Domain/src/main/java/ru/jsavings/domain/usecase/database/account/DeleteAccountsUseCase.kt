package ru.jsavings.domain.usecase.database.account

import io.reactivex.rxjava3.core.Completable
import ru.jsavings.data.model.database.Account
import ru.jsavings.data.repository.database.account.AccountRepository
import ru.jsavings.domain.usecase.common.CompletableUseCase

class DeleteAccountsUseCase(
	override val repository: AccountRepository
) : CompletableUseCase<List<Account>>() {

	override fun buildCompletableUseCase(params: List<Account>): Completable =
		repository.deleteAccounts(params)
}
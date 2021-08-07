package ru.jsavings.domain.usecase.database.account

import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.model.database.Account
import ru.jsavings.data.repository.database.account.AccountRepository
import ru.jsavings.domain.usecase.common.SingleUseCase

class AddAccountUseCase(
	override val repository: AccountRepository
) : SingleUseCase<Long, Account>() {

	override fun buildSingleUseCase(params: Account): Single<Long> =
		repository.createNewAccount(params)
}
package ru.jsavings.presentation.ui.fragments.intro

import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.model.Account
import ru.jsavings.data.model.purse.Purse
import ru.jsavings.domain.usecase.account.GetAllAccountsUseCase
import ru.jsavings.domain.usecase.purse.GetPursesByAccountIdUseCase
import ru.jsavings.presentation.ui.fragments.common.BaseViewModel

class IntroViewModel(
	private val getAllAccountsUseCase: GetAllAccountsUseCase,
	private val getPursesByAccountIdUseCase: GetPursesByAccountIdUseCase
) : BaseViewModel() {

	fun getAllAccounts(): Single<List<Account>> = getAllAccountsUseCase()
	fun getPursesByAccountId(accountId: Int): Single<List<Purse>> = getPursesByAccountIdUseCase()
}
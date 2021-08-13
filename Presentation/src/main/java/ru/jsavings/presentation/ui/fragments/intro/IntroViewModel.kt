package ru.jsavings.presentation.ui.fragments.intro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
<<<<<<< refs/remotes/origin/dev
import ru.jsavings.domain.usecase.cache.CacheUseCase
import ru.jsavings.domain.usecase.database.account.GetAccountsUseCase
=======
import ru.jsavings.domain.usecase.model.Account
import ru.jsavings.domain.usecase.model.binding.AccountWithPurses
import ru.jsavings.domain.usecase.account.DeleteAccountsUseCase
import ru.jsavings.domain.usecase.account.GetAllAccountsWithPursesUseCase
import ru.jsavings.domain.usecase.sharedpreferences.JsSharedPreferencesUseCase
import ru.jsavings.presentation.extensions.default
>>>>>>> Rework started
import ru.jsavings.presentation.ui.fragments.common.BaseViewModel

class IntroViewModel(
	private val getAccountsUseCase: GetAccountsUseCase,
	cacheUseCase: CacheUseCase
) : BaseViewModel(
	getAccountsUseCase,
	cacheUseCase
) {

	private val _allAccountsRequestState = MutableLiveData<RequestState>()
	val allAccountsRequestState =
		_allAccountsRequestState as LiveData<RequestState>

	fun requestAllAccounts() {
		_allAccountsRequestState.postValue(RequestState.SendingState)
		getAccountsUseCase.execute(
			onSuccess = { list ->
				_allAccountsRequestState.postValue(RequestState.SuccessState(list))
			},
			onError = { t ->
				_allAccountsRequestState.postValue(RequestState.ErrorState(t))
			},
			params = Unit
		)
	}
}
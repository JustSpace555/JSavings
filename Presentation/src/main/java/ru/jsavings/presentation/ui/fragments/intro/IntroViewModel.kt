package ru.jsavings.presentation.ui.fragments.intro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.jsavings.domain.usecase.cache.CacheUseCase
import ru.jsavings.domain.usecase.database.account.GetAccountsUseCase
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
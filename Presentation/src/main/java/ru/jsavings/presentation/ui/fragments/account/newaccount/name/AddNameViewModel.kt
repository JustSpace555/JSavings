package ru.jsavings.presentation.ui.fragments.account.newaccount.name

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.jsavings.domain.usecase.database.account.GetAccountsUseCase
import ru.jsavings.presentation.ui.fragments.common.BaseViewModel

class AddNameViewModel(
	private val getAccountsUseCase: GetAccountsUseCase
) : BaseViewModel(getAccountsUseCase) {

	private val _allAccountsRequestState = MutableLiveData<RequestState>()
	val allAccountsRequestState = _allAccountsRequestState as LiveData<RequestState>

	fun requestAllAccounts() {
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
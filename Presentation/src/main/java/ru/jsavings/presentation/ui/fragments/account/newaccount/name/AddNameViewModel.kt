package ru.jsavings.presentation.ui.fragments.account.newaccount.name

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
<<<<<<< refs/remotes/origin/dev
import ru.jsavings.domain.usecase.database.account.GetAccountsUseCase
=======
import ru.jsavings.domain.usecase.model.Account
import ru.jsavings.domain.usecase.account.GetAllAccountsUseCase
import ru.jsavings.domain.usecase.sharedpreferences.NewAccountSharedPreferencesUseCase
import ru.jsavings.presentation.extensions.default
>>>>>>> Rework started
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
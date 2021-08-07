package ru.jsavings.presentation.ui.fragments.account.newaccount.currency

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.jsavings.domain.usecase.network.GetCurrenciesUseCase
import ru.jsavings.presentation.ui.fragments.common.BaseViewModel

class ChooseCurrencyViewModel(
	private val getCurrenciesUseCase: GetCurrenciesUseCase
) : BaseViewModel(getCurrenciesUseCase) {

	var newAccountCurrency: String = ""

	private val _allCurrenciesRequestStateLiveData = MutableLiveData<RequestState>()
	val allCurrenciesRequestStateLiveData =
		_allCurrenciesRequestStateLiveData as LiveData<RequestState>

	fun requestCurrencies() {
		_allCurrenciesRequestStateLiveData.postValue(RequestState.SendingState)
		getCurrenciesUseCase.execute(
			onSuccess = { list ->
				_allCurrenciesRequestStateLiveData.postValue(RequestState.SuccessState(list))
			},
			onError = { t ->
				_allCurrenciesRequestStateLiveData.postValue(RequestState.ErrorState(t))
			},
			params = Unit
		)
	}
}
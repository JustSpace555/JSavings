package ru.jsavings.presentation.ui.fragments.purses.newpurse

import androidx.annotation.ColorInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.jsavings.data.model.database.Account
import ru.jsavings.data.model.database.purse.Purse
import ru.jsavings.data.model.database.purse.PurseCategoryType
import ru.jsavings.domain.interactor.database.DataBaseInteractor
import ru.jsavings.domain.interactor.network.NetworkInteractor
import ru.jsavings.domain.usecase.cache.CacheUseCase
import ru.jsavings.presentation.ui.fragments.common.BaseViewModel

class NewPurseViewModel(
	private val dataBaseInteractor: DataBaseInteractor,
	private val networkInteractor: NetworkInteractor,
	cacheUseCase: CacheUseCase
) : BaseViewModel(
	dataBaseInteractor.addOrUpdateAccountAndCreateNewPurseUseCase,
	networkInteractor.currenciesInteractor.getCurrenciesUseCase,
	networkInteractor.cryptoCoinInteractor.getCryptoCoinsUseCase,
	networkInteractor.currenciesInteractor.convertCurrencyUseCase,
	cacheUseCase
) {

	var purseType = PurseCategoryType.CASH

	var currencyList = mutableListOf<String>()

	@ColorInt
	var purseColor = 0

	private val _currenciesRequestState = MutableLiveData<RequestState>()
	val currenciesRequestState = _currenciesRequestState as LiveData<RequestState>

	fun requestCurrencies() {
		_currenciesRequestState.postValue(RequestState.SendingState)
		networkInteractor.currenciesInteractor.getCurrenciesUseCase.execute(
			onSuccess = { list ->
				_currenciesRequestState.postValue(RequestState.SuccessState(list))
			},
			onError = { t -> _currenciesRequestState.postValue(RequestState.ErrorState(t)) },
			params = Unit
		)
	}



	private val _cryptoCoinRequestState = MutableLiveData<RequestState>()
	val cryptoCoinRequestState = _cryptoCoinRequestState as LiveData<RequestState>

	fun requestCryptoCoins() {
		_cryptoCoinRequestState.postValue(RequestState.SendingState)
		networkInteractor.cryptoCoinInteractor.getCryptoCoinsUseCase.execute(
			onSuccess = { list ->
				_cryptoCoinRequestState.postValue(RequestState.SuccessState(list))
			},
			onError = { t -> _cryptoCoinRequestState.postValue(RequestState.ErrorState(t)) },
			params = Unit
		)
	}



	private val _addOrUpdateAccountAndSavePurseRequest = MutableLiveData<RequestState>()
	val addOrUpdateAccountAndSavePurseRequest =
		_addOrUpdateAccountAndSavePurseRequest as LiveData<RequestState>

	fun requestAddOrUpdateAccountAndSavePurse(account: Account, purse: Purse) {
		_addOrUpdateAccountAndSavePurseRequest.postValue(RequestState.SendingState)
		dataBaseInteractor.addOrUpdateAccountAndCreateNewPurseUseCase.execute(
			onSuccess = { accountId ->
				_addOrUpdateAccountAndSavePurseRequest.postValue(RequestState.SuccessState(accountId))
			},
			onError = { t ->
				_addOrUpdateAccountAndSavePurseRequest.postValue(RequestState.ErrorState(t))
			},
			params = Pair(account, purse)
		)
	}
}
package ru.jsavings.presentation.ui.fragments.purses.newpurse

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.jsavings.data.model.network.crypto.CryptoCoin
import ru.jsavings.domain.usecase.cache.CacheUseCase
import ru.jsavings.domain.usecase.database.account.AddAccountUseCase
import ru.jsavings.domain.usecase.database.purse.AddPurseUseCase
import ru.jsavings.domain.usecase.network.GetAllCoinsUseCase
import ru.jsavings.presentation.extensions.default
import ru.jsavings.presentation.ui.fragments.common.BaseViewModel

class NewPurseViewModel(
	private val addAccountUseCase: AddAccountUseCase,
	private val addPurseUseCase: AddPurseUseCase,
	private val getAllCoinsUseCase: GetAllCoinsUseCase,
	cacheUseCase: CacheUseCase
) : BaseViewModel(addAccountUseCase, addPurseUseCase, getAllCoinsUseCase, cacheUseCase) {

	sealed class CryptoApiRequestState {
		object DefaultState : CryptoApiRequestState()
		object SendingState: CryptoApiRequestState()
		class ErrorState(val t: Throwable): CryptoApiRequestState()
		class OnNextState(val coins: List<CryptoCoin>): CryptoApiRequestState()
	}

	private val _cryptoCoinLiveData = MutableLiveData<CryptoApiRequestState>()
		.default(CryptoApiRequestState.DefaultState)
	val cryptoCoinLiveData = _cryptoCoinLiveData as LiveData<CryptoApiRequestState>

	fun requestCryptoCoins(onError: (t: Throwable) -> Unit) {
		getAllCoinsUseCase.execute(
			onNext = { list ->
				_cryptoCoinLiveData.postValue(CryptoApiRequestState.OnNextState(list))
			},
			onError = onError,
			params = Unit
		)
	}
}
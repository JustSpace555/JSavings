package ru.jsavings.presentation.ui.fragments.purses.newpurse

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.jsavings.data.model.network.crypto.CryptoCoin
import ru.jsavings.domain.usecase.cache.CacheUseCase
import ru.jsavings.domain.usecase.database.account.AddAccountUseCase
import ru.jsavings.domain.usecase.database.purse.AddPurseUseCase
import ru.jsavings.domain.usecase.network.GetAllCoinsUseCase
import ru.jsavings.domain.usecase.network.GetCoinsPriceUseCase
import ru.jsavings.presentation.extensions.default
import ru.jsavings.presentation.ui.fragments.common.BaseViewModel

typealias CoinsPrice = Map<String, Map<String, Int?>>

class NewPurseViewModel(
	private val addAccountUseCase: AddAccountUseCase,
	private val addPurseUseCase: AddPurseUseCase,
	private val getAllCoinsUseCase: GetAllCoinsUseCase,
	private val getCoinsPriceUseCase: GetCoinsPriceUseCase,
	cacheUseCase: CacheUseCase
) : BaseViewModel(
	addAccountUseCase,
	addPurseUseCase,
	getAllCoinsUseCase,
	getCoinsPriceUseCase,
	cacheUseCase
) {

	private val _cryptoCoinLiveData = MutableLiveData<NetworkRequestState<List<CryptoCoin>>>()
		.default(NetworkRequestState.DefaultState())
	val cryptoCoinLiveData = _cryptoCoinLiveData as LiveData<NetworkRequestState<List<CryptoCoin>>>

	private val _cryptoCoinUsdPriceLiveData = MutableLiveData<NetworkRequestState<CoinsPrice>>()
		.default(NetworkRequestState.DefaultState())
	val cryptoCoinUsdPriceLiveData =
		_cryptoCoinUsdPriceLiveData as LiveData<NetworkRequestState<CoinsPrice>>

	fun requestCryptoCoins(onError: (t: Throwable) -> Unit) {
		getAllCoinsUseCase.execute(
			onSuccess = { list ->
				_cryptoCoinLiveData.postValue(NetworkRequestState.OnSuccessState(list))
			},
			onError = onError,
			params = Unit
		)
	}

	fun requestCryptoCoinUsdPrice(coinsIds: List<String>, onError: (t: Throwable) -> Unit) {
		getCoinsPriceUseCase.execute(
			onSuccess = { map ->
				_cryptoCoinUsdPriceLiveData.postValue(NetworkRequestState.OnSuccessState(map))
			},
			onError = onError,
			params = Pair(coinsIds, listOf("usd"))
		)
	}
}
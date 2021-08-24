package ru.jsavings.presentation.ui.fragments.wallet.newwallet

import androidx.annotation.ColorInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.jsavings.domain.interactor.network.NetworkInteractor
import ru.jsavings.domain.model.database.Account
import ru.jsavings.domain.model.database.wallet.Wallet
import ru.jsavings.domain.model.database.wallet.WalletCategoryType
import ru.jsavings.domain.model.network.crypto.CryptoCoin
import ru.jsavings.domain.model.network.currency.Currency
import ru.jsavings.domain.usecase.cache.CacheKeys
import ru.jsavings.domain.usecase.cache.CacheUseCase
import ru.jsavings.domain.usecase.database.wallet.InsertNewWalletUseCase
import ru.jsavings.presentation.ui.fragments.common.BaseViewModel
import ru.jsavings.presentation.ui.fragments.common.ThreadProvider
import java.lang.IllegalArgumentException
import java.lang.NumberFormatException

/**
 * ViewModel for [ru.jsavings.presentation.ui.fragments.wallet.newwallet.NewWalletFragment] fragment
 * @param insertNewWalletUseCase [InsertNewWalletUseCase] to interact with wallets' table in database
 * @param cacheUseCase [CacheUseCase] To get [ru.jsavings.domain.usecase.cache.CacheKeys.JS_CURRENT_ACCOUNT] id
 * @param networkInteractor [NetworkInteractor] to get all available data from api
 *
 * @author JustSpace
 */
class NewWalletViewModel(
	private val insertNewWalletUseCase: InsertNewWalletUseCase,
	private val cacheUseCase: CacheUseCase,
	private val networkInteractor: NetworkInteractor,
	compositeDisposable: CompositeDisposable = CompositeDisposable(),
	threadProvider: ThreadProvider = ThreadProvider()
) : BaseViewModel(compositeDisposable, threadProvider) {

	companion object {
		/**
		 * Values for input by user information validation
		 *
		 * @author JustSpace
		 */
		const val VALID_CHECK = 0
		const val INVALID_WALLET_NAME = 1
		const val INVALID_WALLET_TYPE_EMPTY = 1 shl 1
		const val INVALID_WALLET_CURRENCY_EMPTY = 1 shl 2
		const val INVALID_WALLET_CURRENCY = 1 shl 3
		const val INVALID_WALLET_BALANCE = 1 shl 4
	}

	/**
	 * Name of wallet
	 *
	 * @author JustSpace
	 */
	var walletName = ""

	/**
	 * Type of wallet
	 *
	 * @author JustSpace
	 */
	var walletType = ""

	/**
	 * Current list of currencies according to [walletType]
	 *
	 * @author JustSpace
	 */
	var chosenCurrencyList = emptyList<String>()

	/**
	 * Starting balance of new wallet
	 *
	 * @author JustSpace
	 */
	var walletStartingBalance = ""

	/**
	 * Currency of wallet which in [chosenCurrencyList]
	 *
	 * @author JustSpace
	 */
	var walletCurrency = ""
	@ColorInt var walletColor = 0

	/**
	 * List of all standard currencies
	 *
	 * @author JustSpace
	 */
	val currencyList = mutableListOf<String>()

	/**
	 * List of crypto coins
	 *
	 * @author JustSpace
	 */
	val cryptoCoinsList = mutableListOf<String>()

	private val _currenciesRequestState = MutableLiveData<RequestState>()
	/**
	 * State of getting currencies request
	 *
	 * @author JustSpace
	 */
	val currenciesRequestState = _currenciesRequestState as LiveData<RequestState>

	/**
	 * Request to get all currencies from api
	 * @see currenciesRequestState
	 *
	 * @author JustSpace
	 */
	fun requestCurrencies() = networkInteractor.getCurrenciesUseCase().executeUseCase(_currenciesRequestState)

	private val _cryptoCoinRequestState = MutableLiveData<RequestState>()
	/**
	 * State of getting crypto coins from api
	 *
	 * @author JustSpace
	 */
	val cryptoCoinRequestState = _cryptoCoinRequestState as LiveData<RequestState>

	/**
	 * Request to get all crypto coins from api
	 * @see cryptoCoinRequestState
	 *
	 * @author JustSpace
	 */
	fun requestCryptoCoins() = networkInteractor.getCryptoCoinsUseCase().executeUseCase(_cryptoCoinRequestState)

	/**
	 * State of saving new wallet in database request
	 *
	 * @author JustSpace
	 */
	val saveWalletRequestState: LiveData<RequestState> = MutableLiveData()

	/**
	 * Request to save new wallet in database
	 * @see saveWalletRequestState
	 *
	 * @author JustSpace
	 */
	fun requestSaveWallet() {
		saveWalletRequestState as MutableLiveData<RequestState>

		val accountId = cacheUseCase.get(CacheKeys.JS_CURRENT_ACCOUNT, 0L)
		if (accountId == -1L) {
			saveWalletRequestState.postValue(
				RequestState.ErrorState(IllegalArgumentException("Current account id: $accountId"))
			)
			return
		}

		val newWallet = Wallet(
			accountId = accountId,
			balance = walletStartingBalance.toDouble(),
			category = WalletCategoryType.valueOf(walletType),
			color = walletColor,
			currency = walletCurrency.split(' ').first(),
			iconPath = "",
			name = walletName
		)
		insertNewWalletUseCase(newWallet).executeUseCase(saveWalletRequestState)
	}

	/**
	 * Validate all inputted by user data
	 * @return Validation code with bits of checks
	 * @see VALID_CHECK
	 * @see INVALID_WALLET_BALANCE
	 * @see INVALID_WALLET_CURRENCY
	 * @see INVALID_WALLET_CURRENCY_EMPTY
	 * @see INVALID_WALLET_NAME
	 * @see INVALID_WALLET_TYPE_EMPTY
	 *
	 * @author JustSpace
	 */
	fun validateInputData(): Int {
		var currentState = VALID_CHECK

		if (walletName.isEmpty() || walletName.isBlank())
			currentState = currentState or INVALID_WALLET_NAME

		if (walletType.isEmpty())
			currentState = currentState or INVALID_WALLET_TYPE_EMPTY

		currentState = currentState or when {
			walletCurrency.isEmpty() -> INVALID_WALLET_CURRENCY_EMPTY
			walletCurrency !in chosenCurrencyList -> INVALID_WALLET_CURRENCY
			else -> VALID_CHECK
		}

		currentState = currentState or when {
			walletStartingBalance.isEmpty() -> VALID_CHECK
			walletStartingBalance.isBlank() -> INVALID_WALLET_BALANCE
			else -> try {
				walletStartingBalance.toDouble()
				VALID_CHECK
			} catch (e: NumberFormatException) {
				INVALID_WALLET_BALANCE
			}
		}

		return currentState
	}

	/**
	 * Check if certain check is valid
	 * @param validationResult Result of all validation
	 * @param validationCode Code of instance to check
	 * @return True if validation is valid, false otherwise
	 * @see validateInputData
	 * @see INVALID_WALLET_BALANCE
	 * @see INVALID_WALLET_CURRENCY
	 * @see INVALID_WALLET_CURRENCY_EMPTY
	 * @see INVALID_WALLET_NAME
	 * @see INVALID_WALLET_TYPE_EMPTY
	 *
	 * @author JustSpace
	 */
	fun getValidationResult(validationResult: Int, validationCode: Int) =
		validationResult and validationCode == VALID_CHECK
}
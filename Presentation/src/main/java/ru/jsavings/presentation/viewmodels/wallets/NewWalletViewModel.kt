package ru.jsavings.presentation.viewmodels.wallets

import androidx.annotation.ColorInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.disposables.CompositeDisposable
import ru.jsavings.domain.interactor.network.NetworkInteractor
import ru.jsavings.domain.model.database.wallet.Wallet
import ru.jsavings.domain.model.database.wallet.WalletType
import ru.jsavings.domain.usecase.cache.CacheKeys
import ru.jsavings.domain.usecase.cache.CacheUseCase
import ru.jsavings.domain.usecase.database.wallet.InsertNewWalletUseCase
import ru.jsavings.presentation.extension.ThreadProvider
import ru.jsavings.presentation.viewmodels.common.BaseViewModel

/**
 * ViewModel for [ru.jsavings.presentation.ui.fragments.wallets.NewWalletFragment] fragment
 * @param insertNewWalletUseCase [InsertNewWalletUseCase] to interact with wallets' table in database
 * @param cacheUseCase [CacheUseCase] To get [ru.jsavings.domain.usecase.cache.CacheKeys.JS_CURRENT_ACCOUNT] id
 * @param networkInteractor [NetworkInteractor] to get all available data from api
 *
 * @author JustSpace
 */
class NewWalletViewModel(
	private val insertNewWalletUseCase: InsertNewWalletUseCase,
	private val cacheUseCase: CacheUseCase,
	private val networkInteractor: NetworkInteractor
) : BaseViewModel(CompositeDisposable(), ThreadProvider()) {

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
	 * Starting balance of new wallet
	 *
	 * @author JustSpace
	 */
	var walletStartingBalance = ""

	/**
	 * Currency of new wallet
	 *
	 * @author JustSpace
	 */
	var walletCurrency = ""

	/**
	 * Color of new wallet
	 *
	 * @author JustSpace
	 */
	@ColorInt
	var walletColor = 0

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

	private val _saveWalletRequestState = MutableLiveData<RequestState>()
	/**
	 * State of saving new wallet in database request
	 *
	 * @author JustSpace
	 */
	val saveWalletRequestState: LiveData<RequestState> = _saveWalletRequestState

	/**
	 * Request to save new wallet in database
	 * @see saveWalletRequestState
	 *
	 * @author JustSpace
	 */
	fun requestSaveWallet() {
		val accountId = cacheUseCase.get(CacheKeys.JS_CURRENT_ACCOUNT, 0L)

		val newWallet = Wallet(
			accountId = accountId,
			balance = if (walletStartingBalance.isEmpty()) 0.0 else walletStartingBalance.toDouble(),
			type = WalletType.valueOf(walletType),
			color = walletColor,
			currency = walletCurrency.split(' ').first(),
			iconPath = "",
			name = walletName
		)
		insertNewWalletUseCase(newWallet).executeUseCase(_saveWalletRequestState)
	}

	/**
	 * Validate all inputted by user data
	 * @param currencyList List of current chosen currency
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
	fun validateInputData(currencyList: List<String>): Int {
		var currentState = VALID_CHECK

		if (walletName.isEmpty() || walletName.isBlank())
			currentState = currentState or INVALID_WALLET_NAME

		if (walletType.isEmpty())
			currentState = currentState or INVALID_WALLET_TYPE_EMPTY

		currentState = currentState or when {
			walletCurrency.isEmpty() -> INVALID_WALLET_CURRENCY_EMPTY
			//TODO подумать над тем, что лучшие - жертвовать памятью (list.map { it.toString() }) или временем (list.contains(...))
			currencyList.binarySearch(walletCurrency) < 0 -> INVALID_WALLET_CURRENCY
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
	 * @return True if validation is INVALID, false otherwise
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
		validationResult and validationCode == validationCode
}
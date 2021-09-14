package ru.jsavings.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.disposables.CompositeDisposable
import ru.jsavings.domain.interactor.database.DataBaseInteractor
import ru.jsavings.domain.model.database.Account
import ru.jsavings.domain.usecase.cache.CacheKeys
import ru.jsavings.domain.usecase.cache.CacheUseCase
import ru.jsavings.presentation.extension.ThreadProvider
import ru.jsavings.presentation.viewmodels.common.BaseViewModel

/**
 * Main shared view model for [ru.jsavings.presentation.ui.fragments.IntroFragment],
 * [ru.jsavings.presentation.ui.fragments.transactions.alltransactions.TransactionsFragment],
 * [ru.jsavings.presentation.ui.fragments.CalendarFragment],
 * [ru.jsavings.presentation.ui.fragments.GraphFragment] and
 * [ru.jsavings.presentation.ui.fragments.wallets.WalletsFragment]
 * @param dataBaseInteractor [DataBaseInteractor] to interact with database
 * @param cacheUseCase [CacheUseCase] to interact with cache
 *
 * @author JustSpace
 */
class MainSharedViewModel(
	private val dataBaseInteractor: DataBaseInteractor,
	private val cacheUseCase: CacheUseCase
) : BaseViewModel(CompositeDisposable(), ThreadProvider()) {

	/**
	 * Current account in app
	 *
	 * @author JustSpace
	 */
	lateinit var currentAccount: Account
		private set

	private val _requestAccountState = MutableLiveData<RequestState>()
	/**
	 * Livedata for getting account from database request state
	 * @see currentAccount
	 * @see BaseViewModel.RequestState
	 *
	 * @author JustSpace
	 */
	val requestAccountState: LiveData<RequestState> = _requestAccountState

	/**
	 * Request account by [CacheKeys.JS_CURRENT_ACCOUNT] id
	 * @see requestAccountState
	 *
	 * @author JustSpace
	 */
	fun requestAccount() {
		if (::currentAccount.isInitialized) {
			_requestAccountState.postValue(RequestState.SuccessState(currentAccount))
			return
		}

		val accountId = cacheUseCase.get(CacheKeys.JS_CURRENT_ACCOUNT, -1L)
		if (accountId == -1L) {
			_requestAccountState.postValue(RequestState.ErrorState(ClassNotFoundException()))
			return
		}

		dataBaseInteractor.accountInteractor.getAccountByIdUseCase(accountId)
			.executeUseCase(_requestAccountState) { currentAccount = it }
	}

	private val _requestWalletsState = MutableLiveData<RequestState>()
	/**
	 * Livedata for getting all wallets from database according to [CacheKeys.JS_CURRENT_ACCOUNT] account id request
	 * state
	 * @see BaseViewModel.RequestState
	 *
	 * @author JustSpace
	 */
	val requestWalletsState: LiveData<RequestState> = _requestWalletsState

	/**
	 * Request wallets from database according to [CacheKeys.JS_CURRENT_ACCOUNT] account id
	 * @see requestWalletsState
	 *
	 * @author JustSpace
	 */
	fun requestWallets() = dataBaseInteractor.walletInteractor
		.getWalletsByAccountIdUseCase(currentAccount.accountId)
		.executeUseCase(_requestWalletsState)

	private val _requestTransactionsState = MutableLiveData<RequestState>()
	/**
	 * Livedata for getting all transactions from database according to [CacheKeys.JS_CURRENT_ACCOUNT] account id
	 * request state
	 * @see BaseViewModel.RequestState
	 *
	 * @author JustSpace
	 */
	val requestTransactionsState: LiveData<RequestState> = _requestTransactionsState

	/**
	 * Request transactions from database according to [CacheKeys.JS_CURRENT_ACCOUNT] account id
	 * @see requestTransactionsState
	 *
	 * @author JustSpace
	 */
	fun requestTransactions() = dataBaseInteractor.transactionInteractor
		.getAllTransactionsByAccountIdUseCase(currentAccount.accountId)
		.executeUseCase(_requestTransactionsState)
}
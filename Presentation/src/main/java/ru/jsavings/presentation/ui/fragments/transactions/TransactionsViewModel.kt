package ru.jsavings.presentation.ui.fragments.transactions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.disposables.CompositeDisposable
import ru.jsavings.domain.interactor.database.DataBaseInteractor
import ru.jsavings.domain.model.database.Account
import ru.jsavings.domain.model.database.wallet.Wallet
import ru.jsavings.domain.usecase.cache.CacheKeys
import ru.jsavings.domain.usecase.cache.CacheUseCase
import ru.jsavings.presentation.ui.fragments.common.BaseViewModel
import ru.jsavings.presentation.ui.fragments.common.ThreadProvider
import java.lang.IllegalArgumentException
import java.util.*

class TransactionsViewModel(
    private val dataBaseInteractor: DataBaseInteractor,
    private val cacheUseCase: CacheUseCase,
    compositeDisposable: CompositeDisposable = CompositeDisposable(),
    threadProvider: ThreadProvider = ThreadProvider()
) : BaseViewModel(compositeDisposable, threadProvider) {

	/**
	 * Current account
	 *
	 * @author Михаил Мошков
	 */
	lateinit var account: Account

	private val _requestAccountState = MutableLiveData<RequestState>()
	/**
	 * Livedata for getting account from database request's state
	 *
	 * @author Михаил Мошков
	 */
	val requestAccountState = _requestAccountState as LiveData<RequestState>

	/**
	 * Request account information from database
	 *
	 * @author Михаил Мошков
	 */
	fun requestAccount() {
		val accountId = cacheUseCase.get(CacheKeys.JS_CURRENT_ACCOUNT, -1L)
		if (accountId == -1L) {
			_requestAccountState.postValue(RequestState.ErrorState(IllegalArgumentException("Account id = -1")))
			return
		}

		dataBaseInteractor.accountInteractor.getAccountByIdUseCase(accountId).executeUseCase(_requestAccountState)
	}

	private val _requestWalletsState = MutableLiveData<RequestState>()
	/**
	 * Livedata of getting all account's wallets
	 *
	 * @author Михаил Мошков
	 */
	val requestWalletsState = _requestWalletsState as LiveData<RequestState>

	/**
	 * Request wallets by account id
	 *
	 * @author Михаил Мошков
	 */
	fun requestWallets() = dataBaseInteractor.walletInteractor
		.getWalletsByAccountIdUseCase(account.accountId)
		.executeUseCase(_requestWalletsState)

	private val _requestTransactionsState = MutableLiveData<RequestState>()
	/**
	 * Livedata of getting all transactions by account id request's state
	 *
	 * @author Михаил Мошков
	 */
	val requestTransactionsState = _requestTransactionsState as LiveData<RequestState>

	/**
	 * Request transactions from database by account id
	 * @param timePeriod Period of transactions
	 *
	 * @author Михаил Мошков
	 */
	fun requestTransactions(timePeriod: Pair<Date, Date>) = dataBaseInteractor.transactionInteractor
		.getTransactionsByAccountIdUseCase(account.accountId, timePeriod)
		.executeUseCase(_requestTransactionsState)

	private val _requestLastTransactionDateState = MutableLiveData<RequestState>()
	/**
	 * Livedata of getting date of last transaction in account
	 *
	 * @author Михаил Мошков
	 */
	val requestLastTransactionDateState = _requestLastTransactionDateState as LiveData<RequestState>

	/**
	 * Request last transaction on account by it's id
	 * @author Михаил Мошков
	 */
	fun requestLastTransactionDate() = dataBaseInteractor.transactionInteractor
		.getLastTransactionTimeUseCase(account.accountId)
		.executeUseCase(_requestLastTransactionDateState)
}
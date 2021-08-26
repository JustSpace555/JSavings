package ru.jsavings.presentation.ui.fragments.transactions.newtransaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.disposables.CompositeDisposable
import ru.jsavings.domain.interactor.database.DataBaseInteractor
import ru.jsavings.domain.model.database.wallet.Wallet
import ru.jsavings.domain.usecase.cache.CacheKeys
import ru.jsavings.domain.usecase.cache.CacheUseCase
import ru.jsavings.presentation.ui.fragments.common.BaseViewModel
import ru.jsavings.presentation.ui.fragments.common.ThreadProvider
import java.lang.IllegalArgumentException

/**
 * ViewModel for NewTransactionFragment
 * @param dataBaseInteractor Interactor for getting wallets and save transactions
 * @param cacheUseCase UseCase for getting current account id
 * @param compositeDisposable [CompositeDisposable]
 * @param threadProvider [ThreadProvider]
 *
 * @author Михаил Мошков
 */
class NewTransactionViewModel(
	private val dataBaseInteractor: DataBaseInteractor,
	private val cacheUseCase: CacheUseCase,
	compositeDisposable: CompositeDisposable = CompositeDisposable(),
	threadProvider: ThreadProvider = ThreadProvider()
) : BaseViewModel(compositeDisposable, threadProvider) {

	var transactionDate = ""
	var transactionTime = ""
	var transactionType = ""
	var transactionCategoryId = -1L
	var fromWalletId = -1L
	var toWalletId = -1L
	var transactionSum = ""
	var description = ""

	val wallets = mutableListOf<Wallet>()

	private val _requestAllWalletsState = MutableLiveData<RequestState>()

	/**
	 * LiveData of getting all wallets request
	 *
	 * @author Михаил Мошков
	 */
	val requestAllWalletsState = _requestAllWalletsState as LiveData<RequestState>

	/**
	 * Request all wallets from database by current account id
	 *
	 * @author Михаил Мошков
	 */
	fun requestAllWallets() {
		val accountId = cacheUseCase.get(CacheKeys.JS_CURRENT_ACCOUNT, -1L)
		if (accountId == -1L) {
			_requestAllWalletsState.postValue(
				RequestState.ErrorState(IllegalArgumentException("Currenct account id = -1"))
			)
			return
		}

		dataBaseInteractor.walletInteractor
			.getWalletsByAccountIdUseCase(accountId)
			.executeUseCase(_requestAllWalletsState)
	}
}
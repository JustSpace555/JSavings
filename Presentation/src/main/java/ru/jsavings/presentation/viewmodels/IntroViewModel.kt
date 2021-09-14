package ru.jsavings.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.disposables.CompositeDisposable
import ru.jsavings.domain.model.database.Account
import ru.jsavings.domain.usecase.cache.CacheKeys
import ru.jsavings.domain.usecase.cache.CacheUseCase
import ru.jsavings.domain.usecase.database.account.GetAllAccountsUseCase
import ru.jsavings.presentation.extension.ThreadProvider
import ru.jsavings.presentation.viewmodels.common.BaseViewModel

/**
 * ViewModel to [ru.jsavings.presentation.ui.fragments.intro.IntroFragment]
 * @param getAllAccountsUseCase [GetAllAccountsUseCase] to get all accounts
 * @param cacheUseCase [CacheUseCase] to check cache
 *
 * @author JustSpace
 */
class IntroViewModel(
	private val getAllAccountsUseCase: GetAllAccountsUseCase,
	private val cacheUseCase: CacheUseCase
) : BaseViewModel(CompositeDisposable(), ThreadProvider()) {

	private val _allAccountsRequestState = MutableLiveData<RequestState>()
	/**
	 * Get all accounts request state livedata
	 *
	 * @author JustSpace
	 */
	val allAccountsRequestState = _allAccountsRequestState as LiveData<RequestState>

	/**
	 * Request all accounts from database
	 *
	 * @author JustSpace
	 */
	fun requestAllAccounts() = getAllAccountsUseCase().executeUseCase(_allAccountsRequestState)

	/**
	 * Get current account id from cache
	 * @return Id of current account
	 *
	 * @author JustSpace
	 */
	fun getAccountFromCache(): Long = cacheUseCase.get(CacheKeys.JS_CURRENT_ACCOUNT, -1L)

	/**
	 * Choose random account id from list of all accounts in database or -1 if there are no accounts
	 * @param list List of all accounts in database
	 * @return Id of chosen account or -1 if there are no accounts in database
	 *
	 * @author JustSpace
	 */
	fun chooseAccount(list: List<Account>): Long = if (list.isNotEmpty()) {
		val chosenAccountId = list.random().accountId
		cacheUseCase.put(CacheKeys.JS_CURRENT_ACCOUNT, chosenAccountId)
		chosenAccountId
	} else -1L
}
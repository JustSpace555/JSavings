package ru.jsavings.presentation.ui.fragments.account.newaccount.currency

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.disposables.CompositeDisposable
import ru.jsavings.domain.model.database.Account
import ru.jsavings.domain.usecase.cache.CacheKeys
import ru.jsavings.domain.usecase.cache.CacheUseCase
import ru.jsavings.domain.usecase.database.account.InsertAccountUseCase
import ru.jsavings.domain.usecase.network.GetCurrenciesUseCase
import ru.jsavings.presentation.ui.fragments.common.BaseViewModel
import ru.jsavings.presentation.ui.fragments.common.ThreadProvider

/**
 * ViewModel for [ru.jsavings.presentation.ui.fragments.account.newaccount.currency.ChooseCurrencyFragment] fragment
 * @param getCurrenciesUseCase [GetCurrenciesUseCase] To get all available currencies
 * @param insertAccountUseCase [InsertAccountUseCase] To insert new account in database
 * @param cacheUseCase [CacheUseCase] to insert new account id to cache
 *
 * @author JustSpace
 */
class ChooseCurrencyViewModel(
	private val getCurrenciesUseCase: GetCurrenciesUseCase,
	private val insertAccountUseCase: InsertAccountUseCase,
	private val cacheUseCase: CacheUseCase,
	compositeDisposable: CompositeDisposable = CompositeDisposable(),
	threadProvider: ThreadProvider = ThreadProvider()
) : BaseViewModel(compositeDisposable, threadProvider) {

	/**
	 * Chosen currency or inputted text by user of new account currency
	 *
	 * @author JustSpace
	 */
	var newAccountCurrency: String = ""

	val listOfCurrencies = mutableListOf<String>()

	private val _allCurrenciesRequestStateLiveData = MutableLiveData<RequestState>()
	/**
	 * State of getting all available currencies request
	 *
	 * @author JustSpace
	 */
	val allCurrenciesRequestStateLiveData = _allCurrenciesRequestStateLiveData as LiveData<RequestState>

	/**
	 * Request all available currencies
	 *
	 * @author JustSpace
	 */
	fun requestCurrencies() = getCurrenciesUseCase().executeUseCase(_allCurrenciesRequestStateLiveData)

	/**
	 * Check if inputted currency is in available currencies list
	 * @return true if [newAccountCurrency] is in [listOfCurrencies], false otherwise
	 *
	 * @author JustSpace
	 */
	fun validateNewAccountCurrency(): Boolean = listOfCurrencies.binarySearch(newAccountCurrency) != -1

	private val _saveAccountRequestLiveData = MutableLiveData<RequestState>()
	/**
	 * Save account request state
	 *
	 * @author JustSpace
	 */
	val saveAccountRequestLiveData = _saveAccountRequestLiveData as LiveData<RequestState>

	/**
	 * Request to save new account in database
	 * @param accountName New name of account
	 *
	 * @author JustSpace
	 */
	fun requestSaveAccount(accountName: String) {

		val newAccountCurrencyCode = newAccountCurrency
			.split(' ')
			.first()
			.removePrefix("(")
			.removeSuffix(")")

		val newAccount = Account(
			name = accountName,
			mainCurrencyCode = newAccountCurrencyCode,
			balanceInMainCurrency = 0.0
		)
		insertAccountUseCase(newAccount).executeUseCase(_saveAccountRequestLiveData)
	}

	/**
	 * Save account's id to cache when it was inserted in database
	 * @param newAccountId Id of new account
	 *
	 * @author JustSpace
	 */
	fun onAccountCreated(newAccountId: Long) = cacheUseCase.put(CacheKeys.JS_CURRENT_ACCOUNT, newAccountId)
}
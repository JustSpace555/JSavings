package ru.jsavings.presentation.viewmodels.account.newaccount

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.disposables.CompositeDisposable
import ru.jsavings.domain.model.database.Account
import ru.jsavings.domain.model.network.currency.Currency
import ru.jsavings.domain.usecase.cache.CacheKeys
import ru.jsavings.domain.usecase.cache.CacheUseCase
import ru.jsavings.domain.usecase.database.account.InsertAccountUseCase
import ru.jsavings.domain.usecase.network.GetCurrenciesUseCase
import ru.jsavings.presentation.extension.ThreadProvider
import ru.jsavings.presentation.viewmodels.common.BaseViewModel

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
	private val cacheUseCase: CacheUseCase
) : BaseViewModel(CompositeDisposable(), ThreadProvider()) {

	/**
	 * Chosen currency or inputted text by user of new account currency
	 *
	 * @author JustSpace
	 */
	var newAccountCurrency: String = ""

	private val _allCurrenciesRequestState = MutableLiveData<RequestState>()
	/**
	 * State of getting all available currencies request
	 *
	 * @author JustSpace
	 */
	val allCurrenciesRequestState = _allCurrenciesRequestState as LiveData<RequestState>

	/**
	 * Request all available currencies
	 *
	 * @author JustSpace
	 */
	fun requestCurrencies() = getCurrenciesUseCase().executeUseCase(_allCurrenciesRequestState)

	/**
	 * Check if inputted currency is in available currencies list
	 * @return true if [newAccountCurrency] is in [listOfCurrencies], false otherwise
	 *
	 * @author JustSpace
	 */
	fun validateNewAccountCurrency(listOfCurrencies: List<Currency>): Boolean =
		listOfCurrencies.map { it.toString() }.binarySearch(newAccountCurrency) >= 0

	private val _saveAccountRequestState = MutableLiveData<RequestState>()
	/**
	 * Save account request state
	 *
	 * @author JustSpace
	 */
	val saveAccountRequestState = _saveAccountRequestState as LiveData<RequestState>

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
		insertAccountUseCase(newAccount).executeUseCase(
			liveData = _saveAccountRequestState,
			doOnSuccess = { newAccountId ->
				cacheUseCase.put(CacheKeys.JS_CURRENT_ACCOUNT, newAccountId)
			},
			transform = { newAccount.copy(accountId = it) }
		)
	}
}
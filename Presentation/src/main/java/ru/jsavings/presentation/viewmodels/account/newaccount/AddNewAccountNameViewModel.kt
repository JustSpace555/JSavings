package ru.jsavings.presentation.viewmodels.account.newaccount

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.disposables.CompositeDisposable
import ru.jsavings.domain.model.database.Account
import ru.jsavings.domain.usecase.database.account.GetAllAccountsUseCase
import ru.jsavings.presentation.extension.ThreadProvider
import ru.jsavings.presentation.viewmodels.common.BaseViewModel

/**
 * ViewModel of [ru.jsavings.presentation.ui.fragments.account.newaccount.name.AddNewAccountNameFragment] fragment
 * @param getAllAccountsUseCase [GetAllAccountsUseCase] to get accounts from database to validate name of new account
 *
 * @author JustSpace
 */
class AddNewAccountNameViewModel(
	private val getAllAccountsUseCase: GetAllAccountsUseCase,
) : BaseViewModel(CompositeDisposable(), ThreadProvider()) {

	/**
	 * Current account name inputted by user
	 *
	 * @author JustSpace
	 */
	var currentInputAccountName: String = ""

	private val _allAccountsRequestState = MutableLiveData<RequestState>()
	/**
	 * Get all accounts from database request state
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
}
package ru.jsavings.presentation.ui.fragments.account.newaccount.name

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.disposables.CompositeDisposable
import ru.jsavings.domain.model.database.Account
import ru.jsavings.domain.usecase.database.account.GetAllAccountsUseCase
import ru.jsavings.presentation.ui.fragments.common.BaseViewModel
import ru.jsavings.presentation.ui.fragments.common.ThreadProvider

/**
 * ViewModel of [ru.jsavings.presentation.ui.fragments.account.newaccount.name.AddNewAccountNameFragment] fragment
 * @param getAllAccountsUseCase [GetAllAccountsUseCase] to get accounts from database to validate name of new account
 *
 * @author JustSpace
 */
class AddNewAccountNameViewModel(
	private val getAllAccountsUseCase: GetAllAccountsUseCase,
	compositeDisposable: CompositeDisposable = CompositeDisposable(),
	threadProvider: ThreadProvider = ThreadProvider()
) : BaseViewModel(compositeDisposable, threadProvider) {

	/**
	 * Current account name inputted by user
	 *
	 * @author JustSpace
	 */
	var currentInputAccountName: String = ""

	/**
	 * All possible states of inputted account name
	 *
	 * @author JustSpace
	 */
	sealed class CurrentInputAccountNameState {
		object EmptyState: CurrentInputAccountNameState()
		object TakenState: CurrentInputAccountNameState()
		object ValidState: CurrentInputAccountNameState()
	}

	/**
	 * Current account inputted name state livedata
	 *
	 * @author JustSpace
	 */
	val currentInputAccountNameState: LiveData<CurrentInputAccountNameState> = MutableLiveData()

	private val allAccounts = mutableListOf<Account>()

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

	/**
	 * Sets all accounts from database to ViewModel's variable [allAccounts]
	 * @param list List of accounts
	 *
	 * @author JustSpace
	 */
	fun setAccounts(list: List<Account>) = allAccounts.addAll(list)

	/**
	 * Validates account name inputted by user and posts state to [currentInputAccountNameState] according to check
	 *
	 * @author JustSpace
	 */
	fun validateNewAccountName() {
		currentInputAccountNameState as MutableLiveData<CurrentInputAccountNameState>
		when {
			currentInputAccountName.isEmpty() || currentInputAccountName.isBlank() ->
				currentInputAccountNameState.postValue(CurrentInputAccountNameState.EmptyState)
			allAccounts.map { account -> account.name }.contains(currentInputAccountName) ->
				currentInputAccountNameState.postValue(CurrentInputAccountNameState.TakenState)
			else -> currentInputAccountNameState.postValue(CurrentInputAccountNameState.ValidState)
		}
	}
}
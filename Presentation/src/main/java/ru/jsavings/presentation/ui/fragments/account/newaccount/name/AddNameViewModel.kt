package ru.jsavings.presentation.ui.fragments.account.newaccount.name

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.jsavings.data.model.Account
import ru.jsavings.domain.usecase.account.GetAllAccountsUseCase
import ru.jsavings.domain.usecase.sharedpreferences.NewAccountSharedPreferencesUseCase
import ru.jsavings.presentation.extensions.default
import ru.jsavings.presentation.ui.fragments.common.BaseViewModel
import kotlin.reflect.KClass

class AddNameViewModel(
	private val getAllAccountsUseCase: GetAllAccountsUseCase,
	private val newAccountSharedPreferencesUseCase: NewAccountSharedPreferencesUseCase
) : BaseViewModel(getAllAccountsUseCase) {

	private val _newAccountName = MutableLiveData<String>().default("")
	val newAccountName = _newAccountName as LiveData<String>

	sealed class AccountNameState {
		object OnEmptyState : AccountNameState()
		object OnReadyState : AccountNameState()
	}

	private val _newAccountNameState = MutableLiveData<AccountNameState>().default(AccountNameState.OnEmptyState)
	val newAccountNameState = _newAccountNameState as LiveData<AccountNameState>

	fun onTextChanged(text: CharSequence?) {
		if (text != null) {
			_newAccountName.postValue(text.toString())
			_newAccountNameState.postValue(
				if (text.isEmpty())
					AccountNameState.OnEmptyState
				else
					AccountNameState.OnReadyState
			)
		} else {
			_newAccountNameState.postValue(AccountNameState.OnEmptyState)
		}
	}

	private val _allAccountsListener = MutableLiveData<List<Account>>().default(emptyList())
	val allAccountsListener = _allAccountsListener as LiveData<List<Account>>

	fun requestAllAccounts(onError: (t: Throwable) -> Unit) {
		getAllAccountsUseCase.execute(
			onSuccess = { _allAccountsListener.postValue(it) },
			onError = onError,
			params = Unit
		)
	}

	fun <T> putToSharedPreferences(key: String, value: T) = newAccountSharedPreferencesUseCase.putValue(key, value)
	fun <T : Any> getFromSharedPreferences(key: String, kClass: KClass<T>, defaultValue: T) =
		newAccountSharedPreferencesUseCase.getValue(key, kClass, defaultValue)
}
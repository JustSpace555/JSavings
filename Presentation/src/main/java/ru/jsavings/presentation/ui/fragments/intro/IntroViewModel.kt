package ru.jsavings.presentation.ui.fragments.intro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.jsavings.data.model.Account
import ru.jsavings.data.model.binding.AccountWithPurses
import ru.jsavings.domain.usecase.account.DeleteAccountsUseCase
import ru.jsavings.domain.usecase.account.GetAllAccountsWithPursesUseCase
import ru.jsavings.domain.usecase.sharedpreferences.JsSharedPreferencesUseCase
import ru.jsavings.presentation.extensions.default
import ru.jsavings.presentation.ui.fragments.common.BaseViewModel
import kotlin.reflect.KClass

class IntroViewModel(
	private val getAllAccountsWithPursesUseCase: GetAllAccountsWithPursesUseCase,
	private val deleteAccountsUseCase: DeleteAccountsUseCase,
	private val jsSharedPreferencesUseCase: JsSharedPreferencesUseCase
) : BaseViewModel(getAllAccountsWithPursesUseCase, deleteAccountsUseCase) {

	private val _allAccountsWithPursesLiveData = MutableLiveData<List<AccountWithPurses>>()
	val allAccountsWithPursesLiveData = _allAccountsWithPursesLiveData as LiveData<List<AccountWithPurses>>

	fun requestAllAccountsWithPurses(onError: (t: Throwable) -> Unit, onFinish: () -> Unit) {
		_sqlStatusListener.postValue(SQLStatus.RunningStatus)
		getAllAccountsWithPursesUseCase.execute(
			onSuccess = { _allAccountsWithPursesLiveData.postValue(it) },
			onError = onError,
			onFinish = onFinish,
			params = Unit
		)
	}

	fun requestDeleteAccounts(accounts: List<Account>, onError: (t: Throwable) -> Unit) {
		_sqlStatusListener.postValue(SQLStatus.RunningStatus)
		deleteAccountsUseCase.execute(
			onComplete = { _sqlStatusListener.postValue(SQLStatus.FinishStatus) },
			onError = onError,
			params = accounts
		)
	}



	sealed class SQLStatus {
		object DefaultStatus : SQLStatus()
		object RunningStatus : SQLStatus()
		object FinishStatus : SQLStatus()
	}

	private val _sqlStatusListener = MutableLiveData<SQLStatus>().default(SQLStatus.DefaultStatus)
	val sqlStatusListener = _sqlStatusListener as LiveData<SQLStatus>

	fun <T> putToSharedPreferences(key: String, value: T) = jsSharedPreferencesUseCase.putValue(key, value)
	fun <T : Any> getFromSharedPreferences(key: String, kClass: KClass<T>, defaultValue: T) =
		jsSharedPreferencesUseCase.getValue(key, kClass, defaultValue)
}
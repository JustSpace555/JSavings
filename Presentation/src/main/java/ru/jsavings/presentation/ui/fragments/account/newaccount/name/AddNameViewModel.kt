package ru.jsavings.presentation.ui.fragments.account.newaccount.name

import androidx.lifecycle.MutableLiveData
import ru.jsavings.presentation.extensions.default
import ru.jsavings.presentation.ui.fragments.common.BaseViewModel

class AddNameViewModel : BaseViewModel() {

	val newAccountName = MutableLiveData<String>().default("")

	sealed class AccountNameState {
		object OnEmptyState : AccountNameState()
		object OnReadyState : AccountNameState()
	}
	val newAccountNameState = MutableLiveData<AccountNameState>().default(AccountNameState.OnEmptyState)

	fun onTextChanged(text: CharSequence?) {
		if (text != null) {
			newAccountName.value = text.toString()
			newAccountNameState.value =
				if (text.isEmpty())
					AccountNameState.OnEmptyState
				else
					AccountNameState.OnReadyState
		} else {
			newAccountNameState.value = AccountNameState.OnEmptyState
		}
	}
}
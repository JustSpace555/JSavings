package ru.jsavings.presentation.ui.fragments.newpurse

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.jsavings.presentation.extensions.default

sealed class AccountNameState {
	object OnEmptyState : AccountNameState()
	object OnReadyState : AccountNameState()
}

class AddNewAccountNameViewModel : ViewModel() {

	val newAccountName = MutableLiveData<String>().default("")
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
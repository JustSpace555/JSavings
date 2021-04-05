package ru.jsavings.presentation.ui.fragments.transactions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.jsavings.presentation.ui.fragments.common.BaseViewModel

class TransactionsViewModel : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}
package ru.jsavings.presentation.ui.fragments.common

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseViewModel : ViewModel() {
	val disposables by lazy { mutableListOf<Disposable>() }

	override fun onCleared() {
		disposables.onEach { it.dispose() }
		super.onCleared()
	}
}
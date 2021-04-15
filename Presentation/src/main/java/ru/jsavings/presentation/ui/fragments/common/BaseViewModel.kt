package ru.jsavings.presentation.ui.fragments.common

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import ru.jsavings.domain.usecase.common.BaseUseCase

abstract class BaseViewModel (vararg useCases: BaseUseCase) : ViewModel() {

	private val useCasesList = listOf(*useCases)

	override fun onCleared() {
		super.onCleared()
		useCasesList.onEach { it.dispose() }
	}
}
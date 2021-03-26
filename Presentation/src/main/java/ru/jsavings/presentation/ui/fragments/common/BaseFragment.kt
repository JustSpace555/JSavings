package ru.jsavings.presentation.ui.fragments.common

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseFragment : Fragment() {

	protected abstract val viewModel: ViewModel
	protected val disposable = mutableListOf<Disposable>()
}

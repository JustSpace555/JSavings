package ru.jsavings.presentation.ui.fragments.common

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseFragment : Fragment() {

	protected abstract val viewModel: ViewModel

	protected abstract val bindingUtil: ViewBinding

	protected val disposable by lazy { mutableListOf<Disposable>() }
}

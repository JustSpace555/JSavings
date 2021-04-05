package ru.jsavings.presentation.ui.fragments.common

import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment : Fragment() {

	protected abstract val viewModel: BaseViewModel

	protected abstract val bindingUtil: ViewBinding
}

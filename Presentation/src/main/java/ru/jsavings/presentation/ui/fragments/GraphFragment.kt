package ru.jsavings.presentation.ui.fragments

import org.koin.android.viewmodel.ext.android.sharedViewModel
import ru.jsavings.databinding.FragmentGraphBinding
import ru.jsavings.presentation.ui.fragments.common.BaseFragment
import ru.jsavings.presentation.viewmodels.MainSharedViewModel

class GraphFragment : BaseFragment() {

    override val viewModel by sharedViewModel<MainSharedViewModel>()

    private val bindingUtil get() = binding as FragmentGraphBinding
}
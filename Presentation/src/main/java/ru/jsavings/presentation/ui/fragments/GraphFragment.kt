package ru.jsavings.presentation.ui.fragments

import org.koin.android.viewmodel.ext.android.viewModel
import ru.jsavings.databinding.FragmentGraphBinding
import ru.jsavings.presentation.ui.fragments.common.BaseFragment
import ru.jsavings.presentation.viewmodels.GraphViewModel

class GraphFragment : BaseFragment() {

    override val viewModel by viewModel<GraphViewModel>()

    override lateinit var bindingUtil: FragmentGraphBinding
}
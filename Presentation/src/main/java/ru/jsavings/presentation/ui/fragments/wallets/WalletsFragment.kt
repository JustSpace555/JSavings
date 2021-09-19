package ru.jsavings.presentation.ui.fragments.wallets

import org.koin.android.viewmodel.ext.android.sharedViewModel
import ru.jsavings.databinding.FragmentWalletsBinding
import ru.jsavings.presentation.ui.fragments.common.BaseFragment
import ru.jsavings.presentation.viewmodels.MainSharedViewModel

class WalletsFragment : BaseFragment() {

    override val viewModel by sharedViewModel<MainSharedViewModel>()

    private val bindingUtil get() = binding as FragmentWalletsBinding
}
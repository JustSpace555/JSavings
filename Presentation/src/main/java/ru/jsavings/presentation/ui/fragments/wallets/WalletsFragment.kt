package ru.jsavings.presentation.ui.fragments.wallets

import org.koin.android.viewmodel.ext.android.viewModel
import ru.jsavings.databinding.FragmentWalletsBinding
import ru.jsavings.presentation.ui.fragments.common.BaseFragment
import ru.jsavings.presentation.viewmodels.wallets.WalletsViewModel

class WalletsFragment : BaseFragment() {

    override val viewModel by viewModel<WalletsViewModel>()

    override lateinit var bindingUtil: FragmentWalletsBinding
}
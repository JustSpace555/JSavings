package ru.jsavings.presentation.ui.fragments.wallet.allwallets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.koin.android.viewmodel.ext.android.viewModel
import ru.jsavings.R
import ru.jsavings.databinding.MainFragmentWalletsBinding
import ru.jsavings.presentation.ui.fragments.common.BaseFragment

class WalletsFragment : BaseFragment() {

    override val viewModel by viewModel<WalletsViewModel>()

    override val bindingUtil by lazy { MainFragmentWalletsBinding.inflate(layoutInflater) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.main_fragment_wallets, container, false)
    }
}
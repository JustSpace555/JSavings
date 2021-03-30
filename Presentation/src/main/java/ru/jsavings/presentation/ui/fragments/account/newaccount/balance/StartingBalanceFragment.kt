package ru.jsavings.presentation.ui.fragments.account.newaccount.balance

import org.koin.android.viewmodel.ext.android.viewModel
import ru.jsavings.databinding.NewAccountFragmentStartingBalanceBinding
import ru.jsavings.presentation.ui.fragments.common.BaseFragment

class StartingBalanceFragment : BaseFragment() {

	override val viewModel by viewModel<StartingBalanceViewModel>()

	override val bindingUtil by lazy { NewAccountFragmentStartingBalanceBinding.inflate(layoutInflater) }
}
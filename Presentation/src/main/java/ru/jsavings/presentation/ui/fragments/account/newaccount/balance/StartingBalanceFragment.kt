package ru.jsavings.presentation.ui.fragments.account.newaccount.balance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.koin.android.viewmodel.ext.android.viewModel
import ru.jsavings.databinding.NewAccountFragmentStartingBalanceBinding
import ru.jsavings.presentation.ui.fragments.common.BaseFragment

class StartingBalanceFragment : BaseFragment() {

	override val viewModel by viewModel<StartingBalanceViewModel>()

	override lateinit var bindingUtil : NewAccountFragmentStartingBalanceBinding

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		bindingUtil = NewAccountFragmentStartingBalanceBinding.inflate(inflater, container, false)
		return bindingUtil.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		with(bindingUtil) {
			buttonNewAccountNext.isEnabled = false
		}
	}
}
package ru.jsavings.presentation.ui.fragments.account.newaccount.currency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.koin.android.viewmodel.ext.android.viewModel
import ru.jsavings.R
import ru.jsavings.databinding.NewAccountFragmentChooseCurrencyBinding
import ru.jsavings.presentation.ui.fragments.common.BaseFragment

class ChooseCurrencyFragment : BaseFragment() {

	override val viewModel by viewModel<ChooseCurrencyViewModel>()

	override val bindingUtil by lazy { NewAccountFragmentChooseCurrencyBinding.inflate(layoutInflater) }

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.new_account_fragment_choose_currency, container, false)
	}
}
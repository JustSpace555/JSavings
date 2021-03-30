package ru.jsavings.presentation.ui.fragments.purses.newpurse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.koin.android.viewmodel.ext.android.viewModel
import ru.jsavings.R
import ru.jsavings.databinding.PurseFragmentNewPurseBinding
import ru.jsavings.presentation.ui.fragments.common.BaseFragment

class NewPurseFragment : BaseFragment() {

	override val viewModel by viewModel<NewPurseViewModel>()

	override val bindingUtil by lazy { PurseFragmentNewPurseBinding.inflate(layoutInflater) }

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.purse_fragment_new_purse, container, false)
	}
}
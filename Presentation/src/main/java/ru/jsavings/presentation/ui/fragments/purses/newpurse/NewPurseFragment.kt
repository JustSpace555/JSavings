package ru.jsavings.presentation.ui.fragments.purses.newpurse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.koin.android.viewmodel.ext.android.viewModel
import ru.jsavings.databinding.PurseFragmentNewPurseBinding
import ru.jsavings.presentation.ui.fragments.common.BaseFragment

class NewPurseFragment : BaseFragment() {

	override val viewModel by viewModel<NewPurseViewModel>()

	override lateinit var bindingUtil : PurseFragmentNewPurseBinding

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		bindingUtil = PurseFragmentNewPurseBinding.inflate(inflater, container, false)
		return bindingUtil.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)


	}
}
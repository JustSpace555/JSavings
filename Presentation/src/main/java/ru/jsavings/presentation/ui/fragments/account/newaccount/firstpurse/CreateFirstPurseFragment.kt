package ru.jsavings.presentation.ui.fragments.account.newaccount.firstpurse

import org.koin.android.viewmodel.ext.android.viewModel
import ru.jsavings.databinding.NewAccountFragmentCreatePurseBinding
import ru.jsavings.presentation.ui.fragments.common.BaseFragment

class CreateFirstPurseFragment : BaseFragment() {

	override val viewModel by viewModel<CreateFirstPurseViewModel>()

	override val bindingUtil by lazy { NewAccountFragmentCreatePurseBinding.inflate(layoutInflater) }
}
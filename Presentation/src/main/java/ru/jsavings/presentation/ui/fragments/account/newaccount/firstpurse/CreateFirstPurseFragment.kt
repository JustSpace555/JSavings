package ru.jsavings.presentation.ui.fragments.account.newaccount.firstpurse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.koin.android.viewmodel.ext.android.viewModel
import ru.jsavings.databinding.NewAccountFragmentCreatePurseBinding
import ru.jsavings.presentation.ui.fragments.common.BaseFragment

class CreateFirstPurseFragment : BaseFragment() {

	override val viewModel by viewModel<CreateFirstPurseViewModel>()

	override lateinit var bindingUtil: NewAccountFragmentCreatePurseBinding

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View {
		bindingUtil = NewAccountFragmentCreatePurseBinding.inflate(inflater, container, false)
		return bindingUtil.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		val args by navArgs<CreateFirstPurseFragmentArgs>()
		bindingUtil.buttonOk.setOnClickListener {
			findNavController().navigate(
				CreateFirstPurseFragmentDirections.actionCreateFirstPurseFragmentToNewPurseFragment(
					isEducationNeeded = true,
					newAccountName = args.newAccountName,
					newAccountMainCurrency = args.newAccounMainCurrency
				)
			)
		}
	}
}
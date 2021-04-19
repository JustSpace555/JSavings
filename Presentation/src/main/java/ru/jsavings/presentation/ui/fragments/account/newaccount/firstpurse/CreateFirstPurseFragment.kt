package ru.jsavings.presentation.ui.fragments.account.newaccount.firstpurse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import org.koin.android.viewmodel.ext.android.viewModel
import ru.jsavings.databinding.NewAccountFragmentCreatePurseBinding
import ru.jsavings.presentation.ui.fragments.common.BaseFragment

class CreateFirstPurseFragment : BaseFragment() {

	override val viewModel by viewModel<CreateFirstPurseViewModel>()

	override lateinit var bindingUtil : NewAccountFragmentCreatePurseBinding

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		bindingUtil = NewAccountFragmentCreatePurseBinding.inflate(inflater, container, false)
		return bindingUtil.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		with(bindingUtil) {

			with(textCreateNewPurse) {
				alpha = 0.0f
				animate()
					.alpha(1.0f)
					.duration = 1000
			}

			with(buttonOk) {
				setOnClickListener {
					findNavController().navigate(
						CreateFirstPurseFragmentDirections.actionCreateFirstPurseFragmentToNewPurseFragment(true)
					)
				}

				alpha = 0.0f
				animate()
					.alpha(1.0f)
					.setDuration(1000)
			}
		}
	}
}
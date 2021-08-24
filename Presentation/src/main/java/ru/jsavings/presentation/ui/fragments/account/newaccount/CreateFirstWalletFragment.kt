package ru.jsavings.presentation.ui.fragments.account.newaccount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ru.jsavings.databinding.NewAccountFragmentCreateWalletBinding
import ru.jsavings.presentation.ui.fragments.common.BaseFragment
import ru.jsavings.presentation.ui.fragments.common.BaseViewModel

class CreateFirstWalletFragment : BaseFragment() {

	override lateinit var viewModel: BaseViewModel

	override lateinit var bindingUtil: NewAccountFragmentCreateWalletBinding

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View {
		bindingUtil = NewAccountFragmentCreateWalletBinding.inflate(inflater, container, false)
		return bindingUtil.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		bindingUtil.buttonOk.setOnClickListener {
			findNavController().navigate(
				CreateFirstWalletFragmentDirections.actionCreateFirstWalletFragmentToNewWalletFragment(true)
			)
		}
	}
}
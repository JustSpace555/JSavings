package ru.jsavings.presentation.ui.fragments.account.newaccount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.jsavings.databinding.FragmentNewAccountCreateWalletBinding
import ru.jsavings.presentation.ui.fragments.common.BaseFragment
import ru.jsavings.presentation.viewmodels.common.BaseViewModel

class CreateFirstWalletFragment : BaseFragment() {

	override lateinit var viewModel: BaseViewModel

	override lateinit var bindingUtil: FragmentNewAccountCreateWalletBinding

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View {
		bindingUtil = FragmentNewAccountCreateWalletBinding.inflate(inflater, container, false)
		return bindingUtil.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		val args by navArgs<CreateFirstWalletFragmentArgs>()
		bindingUtil.buttonOk.setOnClickListener {
			findNavController().navigate(CreateFirstWalletFragmentDirections
				.actionCreateFirstWalletFragmentToNewWalletFragment(args.isEducationNeeded, args.newAccountId)
			)
		}
	}
}
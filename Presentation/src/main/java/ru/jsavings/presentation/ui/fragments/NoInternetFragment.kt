package ru.jsavings.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ru.jsavings.databinding.NoInternetFragmentBinding
import ru.jsavings.presentation.ui.fragments.common.BaseFragment
import ru.jsavings.presentation.ui.fragments.common.BaseViewModel

/**
 * No internet fragment
 *
 * @author JustSpace
 */
class NoInternetFragment : BaseFragment() {

	override lateinit var bindingUtil: NoInternetFragmentBinding
	override lateinit var viewModel: BaseViewModel

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		bindingUtil = NoInternetFragmentBinding.inflate(inflater, container, false)
		return bindingUtil.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		bindingUtil.buttonRetry.setOnClickListener {
			if (isInternetAvailable) findNavController().popBackStack()
		}
	}
}
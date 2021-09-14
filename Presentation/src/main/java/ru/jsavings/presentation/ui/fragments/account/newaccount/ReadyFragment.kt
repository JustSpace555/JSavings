package ru.jsavings.presentation.ui.fragments.account.newaccount

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ru.jsavings.databinding.FragmentNewAccountReadyToStartBinding
import ru.jsavings.presentation.ui.fragments.common.BaseFragment
import ru.jsavings.presentation.viewmodels.common.BaseViewModel

class ReadyFragment : BaseFragment() {

	override lateinit var viewModel: BaseViewModel

	override lateinit var bindingUtil: FragmentNewAccountReadyToStartBinding

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		bindingUtil = FragmentNewAccountReadyToStartBinding.inflate(inflater, container, false)
		return bindingUtil.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		bindingUtil.textIntroReadyToStart
			.animate()
			.alpha(1f)
			.setDuration(3000)
			.setListener(object : AnimatorListenerAdapter() {
				override fun onAnimationEnd(animation: Animator?) {
					super.onAnimationEnd(animation)
					findNavController().navigate(
						ReadyFragmentDirections.actionGlobalFragmentToTransactionsFragment()
					)
				}
			})
			.start()
	}
}
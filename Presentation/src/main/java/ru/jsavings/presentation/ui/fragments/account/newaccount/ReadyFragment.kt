package ru.jsavings.presentation.ui.fragments.account.newaccount

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.koin.android.viewmodel.ext.android.viewModel
import ru.jsavings.R
import ru.jsavings.databinding.NewAccountFragmentReadyToStartBinding
import ru.jsavings.presentation.ui.fragments.common.BaseFragment
import ru.jsavings.presentation.ui.fragments.common.BaseViewModel

class ReadyFragment : BaseFragment() {

	override lateinit var viewModel: BaseViewModel

	override val bindingUtil by lazy { NewAccountFragmentReadyToStartBinding.inflate(layoutInflater) }

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.new_account_fragment_ready_to_start, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		bindingUtil.textIntroReadyToStart.alpha = 0f
		bindingUtil.textIntroReadyToStart
			.animate()
			.alpha(1f)
			.setDuration(3000)
			.setListener(object : AnimatorListenerAdapter() {
				override fun onAnimationEnd(animation: Animator?) {
					super.onAnimationEnd(animation)
					findNavController().navigate(
						ReadyFragmentDirections
							.actionGlobalFragmentToTransactionsFragment()
					)
				}
			})
			.start()
	}
}
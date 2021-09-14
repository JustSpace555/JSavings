package ru.jsavings.presentation.ui.fragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import org.koin.android.viewmodel.ext.android.sharedViewModel
import ru.jsavings.databinding.FragmentIntroBinding
import ru.jsavings.domain.model.database.Account
import ru.jsavings.presentation.ui.activities.MainActivity
import ru.jsavings.presentation.ui.fragments.common.BaseFragment
import ru.jsavings.presentation.viewmodels.MainSharedViewModel

class IntroFragment : BaseFragment() {

	override val viewModel by sharedViewModel<MainSharedViewModel>()
	override lateinit var bindingUtil: FragmentIntroBinding

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View {
		bindingUtil = FragmentIntroBinding.inflate(inflater, container, false)
		return bindingUtil.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		bindingUtil.textIntro.alpha = 0f
		bindingUtil.textIntro.animate()
			.alpha(1f)
			.setDuration(1500)
			.setListener(object : AnimatorListenerAdapter() {
				override fun onAnimationEnd(animation: Animator?) {
					super.onAnimationEnd(animation)
					moveToNextFragment()
				}
			})
	}

	@Suppress("UNCHECKED_CAST")
	private fun moveToNextFragment() {

		viewModel.requestAccountState.subscribe<Account>(
			hideLoading = false,
			onSuccess = {
				(requireActivity() as? MainActivity)?.setAccountName(it.name)
				navigateToTransactionsFragment()
			},
			onError = {
				if (it !is ClassNotFoundException) showTextSnackBar(it.getErrorString())
				navigateToNewAccountFragment()
			}
		)
	}

	private fun navigateToTransactionsFragment() = findNavController().navigate(
		IntroFragmentDirections.actionIntroFragmentToTransactionsFragment()
	)

	private fun navigateToNewAccountFragment() = findNavController().navigate(
		IntroFragmentDirections.actionIntroFragmentToAddNewAccountName(true)
	)
}
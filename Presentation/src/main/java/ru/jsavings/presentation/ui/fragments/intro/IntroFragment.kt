package ru.jsavings.presentation.ui.fragments.intro

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import org.koin.android.viewmodel.ext.android.viewModel
import ru.jsavings.domain.model.database.Account
import ru.jsavings.databinding.IntroFragmentBinding
import ru.jsavings.presentation.ui.fragments.common.BaseFragment
import ru.jsavings.presentation.ui.fragments.common.BaseViewModel

class IntroFragment : BaseFragment() {

	override val viewModel by viewModel<IntroViewModel>()
	override lateinit var bindingUtil: IntroFragmentBinding

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View {
		bindingUtil = IntroFragmentBinding.inflate(inflater, container, false)
		return bindingUtil.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		viewModel.requestAllAccounts()

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

		val accountId = viewModel.getAccountFromCache()
		if (accountId != -1L) {
			navigateToTransactionsFragment()
			return
		}
		else
			viewModel.requestAllAccounts()

		viewModel.allAccountsRequestState.observe(viewLifecycleOwner) { state ->
			when(state) {
				is BaseViewModel.RequestState.SuccessState<*> -> {
					val chosenAccountId = viewModel.chooseAccount(state.data as List<Account>)
					if (chosenAccountId != -1L)
						navigateToTransactionsFragment()
					else
						navigateToNewAccountFragment()
				}
				is BaseViewModel.RequestState.ErrorState<*> -> {
					showTextSnackBar(state.t.getErrorString())
					navigateToNewAccountFragment()
				}
				else -> {}
			}
		}
	}

	private fun navigateToTransactionsFragment() = findNavController().navigate(
		IntroFragmentDirections.actionIntroFragmentToTransactionsFragment()
	)

	private fun navigateToNewAccountFragment() = findNavController().navigate(
		IntroFragmentDirections.actionIntroFragmentToAddNewAccountName(true)
	)
}
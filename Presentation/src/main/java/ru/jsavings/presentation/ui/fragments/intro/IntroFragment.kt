package ru.jsavings.presentation.ui.fragments.intro

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import org.koin.android.viewmodel.ext.android.viewModel
<<<<<<< refs/remotes/origin/dev
import ru.jsavings.data.model.database.Account
import ru.jsavings.data.repository.cache.CacheKeys
=======
import ru.jsavings.R
import ru.jsavings.domain.usecase.model.binding.AccountWithPurses
>>>>>>> Rework started
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

		val currentAccountId = viewModel.getFromCache(CacheKeys.JS_CURRENT_ACCOUNT, -1L)
		if (currentAccountId != -1L) {
			findNavController().navigate(
				IntroFragmentDirections.actionIntroFragmentToTransactionsFragment(currentAccountId)
			)
			return
		}

		viewModel.requestAllAccounts()

		viewModel.allAccountsRequestState.observe(viewLifecycleOwner) { state ->
			when(state) {
				is BaseViewModel.RequestState.SuccessState<*> -> {
					val data = state.data as List<Account>
					if (data.isEmpty()) {
						findNavController().navigate(
							IntroFragmentDirections.actionIntroFragmentToAddNewAccountName(true)
						)
					} else {
						val accountId = data.random().accountId
						viewModel.putToCache(CacheKeys.JS_CURRENT_ACCOUNT, accountId)
						findNavController().navigate(
							IntroFragmentDirections.actionIntroFragmentToTransactionsFragment(
								accountId
							)
						)
					}
				}
				is BaseViewModel.RequestState.ErrorState<*> -> {
					showTextSnackBar(state.t.getErrorString())
					findNavController().navigate(
						IntroFragmentDirections.actionIntroFragmentToAddNewAccountName(true)
					)
				}
				else -> {}
			}
		}
	}
}
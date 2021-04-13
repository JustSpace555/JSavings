package ru.jsavings.presentation.ui.fragments.intro

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.navigation.fragment.findNavController
import org.koin.android.viewmodel.ext.android.viewModel
import ru.jsavings.R
import ru.jsavings.data.model.Account
import ru.jsavings.data.model.binding.AccountWithPurses
import ru.jsavings.databinding.IntroFragmentBinding
import ru.jsavings.presentation.ui.fragments.common.BaseFragment

class IntroFragment : BaseFragment() {

	override val viewModel by viewModel<IntroViewModel>()
	override lateinit var bindingUtil: IntroFragmentBinding

	private var isEducationNeeded = false
	private val allAccountsWithPurses = mutableListOf<AccountWithPurses>()

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		bindingUtil = IntroFragmentBinding.inflate(inflater, container, false)
		return bindingUtil.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		bindingUtil.textIntro.alpha = 0f

		viewModel.allAccountsWithPursesLiveData.observe(viewLifecycleOwner) { list ->
			if (list.isEmpty() || list.all { account -> account.purses.isEmpty() })
				isEducationNeeded = true
			allAccountsWithPurses.addAll(list)
		}
	}

	override fun onStart() {
		super.onStart()
		bindingUtil.textIntro.animate()
			.alpha(1f)
			.setDuration(1500)
			.setListener(
				object : AnimatorListenerAdapter() {
					override fun onAnimationStart(animation: Animator?) {
						super.onAnimationStart(animation)

						viewModel.requestAllAccountsWithPurses(
							onError = {
								showTextSnackBar(
									requireView(), it.localizedMessage ?: getString(R.string.something_went_wrong)
								)
							},
							onFinish = {
								viewModel.requestDeleteAccounts(
									allAccountsWithPurses.filter { it.purses.isEmpty() }.map { it.account }
								) {
									showTextSnackBar(
										requireView(),
										it.localizedMessage ?: getString(R.string.something_went_wrong)
									)
								}
							}
						)
					}

					override fun onAnimationEnd(animation: Animator?) {
						super.onAnimationEnd(animation)

						viewModel.sqlStatusListener.observe(viewLifecycleOwner) { sqlStatus ->

							if (sqlStatus == IntroViewModel.SQLStatus.FinishStatus) {
								bindingUtil.progressBar.visibility = View.GONE
								val action = if (isEducationNeeded) {
									IntroFragmentDirections.actionIntroFragmentToNewAccountNavigation()
								} else {
									val sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
									val currentAccountName = sharedPreferences.getString(
										getString(R.string.sp_current_account), null
									)

									when {
										currentAccountName != null -> IntroFragmentDirections
											.actionIntroFragmentToTransactionsFragment(currentAccountName)

										allAccountsWithPurses.isNotEmpty() -> {
											val chosenAccountName = allAccountsWithPurses
												.minByOrNull { it.account.name }!!
												.account.name
											with(sharedPreferences.edit()) {
												putString(getString(R.string.sp_current_account), chosenAccountName)
												apply()
											}
											IntroFragmentDirections
												.actionIntroFragmentToTransactionsFragment(chosenAccountName)

										}
										else -> {
											showTextSnackBar(
												requireView(),
												NullPointerException().localizedMessage ?:
												getString(R.string.something_went_wrong)
											)
											IntroFragmentDirections.actionIntroFragmentToNewAccountNavigation()
										}
									}
								}
								bindingUtil.progressBar.visibility = View.GONE
								findNavController().navigate(action)

							} else {
								bindingUtil.textIntro.animate()
									.alpha(0.2f)
									.duration = 500
								bindingUtil.progressBar.visibility = View.VISIBLE
							}
						}
					}
				})
	}
}
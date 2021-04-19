package ru.jsavings.presentation.ui.fragments.intro

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import org.koin.android.viewmodel.ext.android.viewModel
import ru.jsavings.R
import ru.jsavings.data.model.binding.AccountWithPurses
import ru.jsavings.data.repository.sharedpreferences.SharedPreferencesConsts
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
									IntroFragmentDirections.actionIntroFragmentToNewAccountNavigation(
										isEducationNeeded = true
									)
								} else {
									val currentAccountName = viewModel.getFromSharedPreferences(
										SharedPreferencesConsts.JsGlobalSP,
										SharedPreferencesConsts.JsGlobalSP.JS_CURRENT_ACCOUNT,
										""
									)

									when {
										currentAccountName.isNotEmpty() -> IntroFragmentDirections
											.actionIntroFragmentToTransactionsFragment(currentAccountName)

										allAccountsWithPurses.isNotEmpty() -> {
											val chosenAccountName = allAccountsWithPurses
												.minByOrNull { it.account.name }!!
												.account.name
											viewModel.putToSharedPreferences(
												SharedPreferencesConsts.JsGlobalSP,
												SharedPreferencesConsts.JsGlobalSP.JS_CURRENT_ACCOUNT,
												chosenAccountName
											)
											IntroFragmentDirections
												.actionIntroFragmentToTransactionsFragment(chosenAccountName)

										}
										else -> {
											showTextSnackBar(
												requireView(),
												NullPointerException().localizedMessage ?:
												getString(R.string.something_went_wrong)
											)
											IntroFragmentDirections.actionIntroFragmentToNewAccountNavigation(
												isEducationNeeded = false
											)
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
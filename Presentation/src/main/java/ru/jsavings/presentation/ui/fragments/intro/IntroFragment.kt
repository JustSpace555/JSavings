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
import ru.jsavings.data.model.database.binding.AccountWithPurses
import ru.jsavings.data.repository.cache.CacheKeys
import ru.jsavings.databinding.IntroFragmentBinding
import ru.jsavings.presentation.ui.fragments.common.BaseFragment

class IntroFragment : BaseFragment() {

	override val viewModel by viewModel<IntroViewModel>()
	override lateinit var bindingUtil: IntroFragmentBinding

	private var isEducationNeeded = false
	private val allAccountsWithPurses = mutableListOf<AccountWithPurses>()

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
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
									allAccountsWithPurses
										.filter { it.purses.isEmpty() }
										.map { it.account }
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
								hideLoading()
								val action = if (isEducationNeeded) {
									IntroFragmentDirections
										.actionIntroFragmentToNewAccountNavigation(
											isEducationNeeded = true
										)
								} else {
									val currentAccountName = viewModel
										.getFromCache(CacheKeys.JS_CURRENT_ACCOUNT, "")

									when {
										currentAccountName.isNotEmpty() -> IntroFragmentDirections
											.actionIntroFragmentToTransactionsFragment(
												currentAccountName
											)

										allAccountsWithPurses.isNotEmpty() -> {
											val chosenAccountName = allAccountsWithPurses
												.minByOrNull { it.account.name }!!
												.account.name
											viewModel.putToCache(
												CacheKeys.JS_CURRENT_ACCOUNT,
												chosenAccountName
											)
											IntroFragmentDirections
												.actionIntroFragmentToTransactionsFragment(
													chosenAccountName
												)
										}
										else -> {
											showTextSnackBar(
												requireView(),
												NullPointerException().localizedMessage ?:
												getString(R.string.something_went_wrong)
											)
											IntroFragmentDirections
												.actionIntroFragmentToNewAccountNavigation(
													isEducationNeeded = false
												)
										}
									}
								}
								hideLoading()
								findNavController().navigate(action)

							} else showLoading()
						}
					}
				})
	}
}
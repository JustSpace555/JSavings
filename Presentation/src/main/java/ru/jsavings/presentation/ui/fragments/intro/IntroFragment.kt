package ru.jsavings.presentation.ui.fragments.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.google.android.material.snackbar.Snackbar
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.android.viewmodel.ext.android.viewModel
import ru.jsavings.R
import ru.jsavings.data.model.Account
import ru.jsavings.data.model.purse.Purse
import ru.jsavings.databinding.IntroFragmentBinding
import ru.jsavings.presentation.ui.fragments.common.BaseFragment

class IntroFragment : BaseFragment() {

	override val viewModel by viewModel<IntroViewModel>()
	override val bindingUtil by lazy { IntroFragmentBinding.inflate(layoutInflater) }

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		val view =  inflater.inflate(R.layout.intro_fragment, container, false)

		viewModel.allAccountsWithPursesLiveData.observe(viewLifecycleOwner) { list ->
			if (list.isEmpty() || list.all { account -> account.purses.isEmpty() })
				isEducationNeeded = true
			accountsWithNoPurse.addAll(list.filter { it.purses.isEmpty() }.map { it.account })
		}

		viewModel.requestAllAccountsWithPurses(

			onError = { showTextSnackBar(
				view, it.localizedMessage ?: R.string.something_went_wrong.toString()
			)},

			onFinish = {
				viewModel.requestDeleteAccounts(accountsWithNoPurse) { showTextSnackBar(
					view, it.localizedMessage ?: R.string.something_went_wrong.toString()
				)}
			}
		)

		return view
	}

	private var isEducationNeeded = false
	private val accountsWithNoPurse = mutableListOf<Account>()

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		val appearanceAnimation = AnimationUtils.loadAnimation(context, R.anim.text_appearance).also { anim ->
			anim.setAnimationListener(object : Animation.AnimationListener {
				override fun onAnimationStart(animation: Animation?) {}
				override fun onAnimationRepeat(animation: Animation?) {}
				
				override fun onAnimationEnd(animation: Animation?) {
					viewModel.sqlStatusListener.observe(viewLifecycleOwner) {
						if (it == IntroViewModel.SQLStatus.FinishStatus)
					}
				}
			})
		}
		
		bindingUtil.textIntro.startAnimation(appearanceAnimation)
	}
}
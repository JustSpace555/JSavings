package ru.jsavings.presentation.ui.fragments.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.android.viewmodel.ext.android.viewModel
import ru.jsavings.R
import ru.jsavings.data.model.purse.Purse
import ru.jsavings.databinding.IntroFragmentBinding
import ru.jsavings.presentation.ui.fragments.common.BaseFragment

class IntroFragment : BaseFragment() {

	override val viewModel by viewModel<IntroViewModel>()

	override val bindingUtil by lazy { IntroFragmentBinding.inflate(layoutInflater) }

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.intro_fragment, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		val appearanceAnimation = AnimationUtils.loadAnimation(context, R.anim.text_appearance)
		bindingUtil.textIntro.startAnimation(appearanceAnimation)

		viewModel.getAllAccounts()
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe({
					   appearanceAnimation.setAnimationListener (object : Animation.AnimationListener {
						   override fun onAnimationStart(animation: Animation?) {}
						   override fun onAnimationRepeat(animation: Animation?) {}
						   override fun onAnimationEnd(animation: Animation?) {
						   	    if ()
						   }
					   })
			}, {
				//TODO Переделать
				throw it
			}).also { viewModel.disposables.add(it) }
	}

	private fun getAllPursesByAccountId(accountId: Int): List<Purse>
}
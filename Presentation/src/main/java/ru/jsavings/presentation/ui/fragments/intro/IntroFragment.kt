package ru.jsavings.presentation.ui.fragments.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import org.koin.android.viewmodel.ext.android.viewModel
import ru.jsavings.R
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
	}
}
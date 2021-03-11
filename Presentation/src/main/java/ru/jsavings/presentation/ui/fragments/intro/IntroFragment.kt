package ru.jsavings.presentation.ui.fragments.intro

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.fragment_intro.*
import ru.jsavings.R
import ru.jsavings.presentation.ui.fragments.common.BaseFragment

class IntroFragment : BaseFragment() {

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		val appearanceAnimation = AnimationUtils.loadAnimation(context, R.anim.text_appearance)
		text_intro.startAnimation(appearanceAnimation)
	}
}
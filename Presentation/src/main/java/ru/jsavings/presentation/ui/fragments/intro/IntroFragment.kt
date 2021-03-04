package ru.jsavings.presentation.ui.fragments.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import ru.jsavings.R
import ru.jsavings.presentation.ui.fragments.common.BaseFragment

class IntroFragment : BaseFragment() {

	private lateinit var textIntro: TextView

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		val root = inflater.inflate(R.layout.fragment_intro, container, false)

		textIntro = root.findViewById(R.id.text_intro)

		return root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		val appearanceAnimation = AnimationUtils.loadAnimation(context, R.anim.text_appearance)
		textIntro.startAnimation(appearanceAnimation)
	}
}
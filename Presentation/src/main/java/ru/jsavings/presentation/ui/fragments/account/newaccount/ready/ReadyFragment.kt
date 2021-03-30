package ru.jsavings.presentation.ui.fragments.account.newaccount.ready

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import org.koin.android.viewmodel.ext.android.viewModel
import ru.jsavings.R
import ru.jsavings.databinding.NewAccountFragmentReadyToStartBinding
import ru.jsavings.presentation.ui.fragments.common.BaseFragment

class ReadyFragment : BaseFragment() {

	override val viewModel by viewModel<ReadyViewModel>()

	override val bindingUtil by lazy { NewAccountFragmentReadyToStartBinding.inflate(layoutInflater) }

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.new_account_fragment_ready_to_start, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		val appearanceAnimation = AnimationUtils.loadAnimation(context, R.anim.text_appearance)
		bindingUtil.textIntroReadyToStart.startAnimation(appearanceAnimation)
	}
}
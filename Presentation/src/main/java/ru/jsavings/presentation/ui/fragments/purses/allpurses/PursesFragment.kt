package ru.jsavings.presentation.ui.fragments.purses.allpurses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.koin.android.viewmodel.ext.android.viewModel
import ru.jsavings.R
import ru.jsavings.databinding.MainFragmentPursesBinding
import ru.jsavings.presentation.ui.fragments.common.BaseFragment

class PursesFragment : BaseFragment() {

    override val viewModel by viewModel<PursesViewModel>()

    override val bindingUtil by lazy { MainFragmentPursesBinding.inflate(layoutInflater) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.main_fragment_purses, container, false)
    }
}
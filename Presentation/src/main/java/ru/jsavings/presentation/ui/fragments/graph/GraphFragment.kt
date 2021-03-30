package ru.jsavings.presentation.ui.fragments.graph

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import org.koin.android.viewmodel.ext.android.viewModel
import ru.jsavings.R
import ru.jsavings.databinding.MainFragmentGraphBinding
import ru.jsavings.presentation.ui.fragments.common.BaseFragment

class GraphFragment : BaseFragment() {

    override val viewModel by viewModel<GraphViewModel>()

    override val bindingUtil by lazy { MainFragmentGraphBinding.inflate(layoutInflater) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.main_fragment_graph, container, false)
    }
}
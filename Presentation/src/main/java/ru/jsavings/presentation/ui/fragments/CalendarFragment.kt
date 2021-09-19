package ru.jsavings.presentation.ui.fragments

import org.koin.android.viewmodel.ext.android.sharedViewModel
import ru.jsavings.databinding.FragmentCalendarBinding
import ru.jsavings.presentation.ui.fragments.common.BaseFragment
import ru.jsavings.presentation.viewmodels.MainSharedViewModel

class CalendarFragment : BaseFragment() {

	override val viewModel by sharedViewModel<MainSharedViewModel>()

	private val bindingUtil get() = binding as FragmentCalendarBinding
}
package ru.jsavings.presentation.ui.fragments

import org.koin.android.viewmodel.ext.android.viewModel
import ru.jsavings.databinding.FragmentCalendarBinding
import ru.jsavings.presentation.ui.fragments.common.BaseFragment
import ru.jsavings.presentation.viewmodels.CalendarViewModel

class CalendarFragment : BaseFragment() {

	override val viewModel by viewModel<CalendarViewModel>()

	override lateinit var bindingUtil: FragmentCalendarBinding
}
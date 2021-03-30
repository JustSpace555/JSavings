package ru.jsavings.presentation.ui.fragments.calendar

import org.koin.android.viewmodel.ext.android.viewModel
import ru.jsavings.databinding.MainFragmentCalendarBinding
import ru.jsavings.presentation.ui.fragments.common.BaseFragment

class CalendarFragment : BaseFragment() {

	override val viewModel by viewModel<CalendarViewModel>()

	override val bindingUtil by lazy { MainFragmentCalendarBinding.inflate(layoutInflater) }
}
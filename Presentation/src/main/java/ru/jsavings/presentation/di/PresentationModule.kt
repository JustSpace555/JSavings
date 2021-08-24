package ru.jsavings.presentation.di

import ru.jsavings.domain.di.domainModule

/**
 * List of all koin modules in [ru.jsavings.presentation] module + [ru.jsavings.domain] list
 *
 * @author JustSpace
 */
val presentationModule = listOf(
	viewModelModule
) + domainModule
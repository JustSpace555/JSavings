package ru.jsavings.domain.di

import ru.jsavings.data.di.dataModule

/**
 * List of all koin modules in [ru.jsavings.domain] and [ru.jsavings.data] modules.
 *
 * @author JustSpace
 */
val domainModule = listOf(
	useCaseModule,
	interactorModule,
	mapperModule
) + dataModule
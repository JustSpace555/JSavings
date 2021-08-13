package ru.jsavings.domain.usecase.sharedpreferences

import ru.jsavings.domain.usecase.common.SharedPreferencesUseCase

class JsSharedPreferencesUseCase(
	private val jsSharedPreferencesRepository: JsSharedPreferencesRepository
) : SharedPreferencesUseCase(jsSharedPreferencesRepository)
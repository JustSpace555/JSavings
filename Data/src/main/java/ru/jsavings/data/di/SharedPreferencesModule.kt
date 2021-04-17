package ru.jsavings.data.di

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.jsavings.data.repository.sharedpreferences.JsSharedPreferences
import ru.jsavings.data.repository.sharedpreferences.NewAccountSharedPreferences

internal val sharedPreferencesModule = module {

	single(named(JsSharedPreferences::class.java.simpleName)) {
		androidContext().getSharedPreferences(JsSharedPreferences.FILE_NAME, Context.MODE_PRIVATE)
	}

	single(named(NewAccountSharedPreferences::class.java.simpleName)) {
		androidContext().getSharedPreferences(NewAccountSharedPreferences.FILE_NAME, Context.MODE_PRIVATE)
	}
}
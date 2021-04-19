package ru.jsavings.data.di

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.jsavings.data.repository.sharedpreferences.SharedPreferencesConsts

internal val sharedPreferencesModule = module {

	single(named(SharedPreferencesConsts.JsGlobalSP::class.java.simpleName)) {
		androidContext().getSharedPreferences(
			SharedPreferencesConsts.JsGlobalSP.FILE_NAME,
			Context.MODE_PRIVATE
		)
	}

	single(named(SharedPreferencesConsts.NewAccountSP::class.java.simpleName)) {
		androidContext().getSharedPreferences(
			SharedPreferencesConsts.NewAccountSP.FILE_NAME,
			Context.MODE_PRIVATE
		)
	}
}
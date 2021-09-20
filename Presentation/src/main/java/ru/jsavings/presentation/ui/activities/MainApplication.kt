package ru.jsavings.presentation.ui.activities

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import ru.jsavings.presentation.di.presentationModule

class MainApplication : Application() {

	override fun onCreate() {
		super.onCreate()

		startKoin {
			loadKoinModules(presentationModule)
			androidContext(this@MainApplication)
		}
	}
}
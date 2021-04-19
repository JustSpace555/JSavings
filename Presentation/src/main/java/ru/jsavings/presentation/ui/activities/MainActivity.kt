package ru.jsavings.presentation.ui.activities

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.qualifier.named
import ru.jsavings.R
import ru.jsavings.data.di.dataModule
import ru.jsavings.data.repository.sharedpreferences.SharedPreferencesConsts
import ru.jsavings.domain.usecase.di.domainModule
import ru.jsavings.presentation.di.presentationModule

class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		startKoin {
			androidContext(this@MainActivity)
			loadKoinModules(dataModule + domainModule + presentationModule)
		}

		setContentView(R.layout.activity_main)

		val navController = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)!!.findNavController()
		val appBarConfiguration = AppBarConfiguration(
			setOf(
				R.id.transactions_fragment, R.id.calendar_fragment, R.id.graph_fragment, R.id.purses_fragment
			)
		)
		setupActionBarWithNavController(navController, appBarConfiguration)
		findViewById<BottomNavigationView>(R.id.nav_view).apply {
			setupWithNavController(navController)
			background = null
			visibility = View.GONE
		}
		findViewById<FloatingActionButton>(R.id.add_transaction_fab).visibility = View.GONE
		findViewById<BottomAppBar>(R.id.bottom_app_bar).visibility = View.GONE

		supportActionBar?.hide()
	}

	override fun onDestroy() {
		super.onDestroy()
		val sp by inject<SharedPreferences>(named(SharedPreferencesConsts.NewAccountSP::class.java.simpleName))
		with(sp.edit()) {
			clear()
			apply()
		}
		stopKoin()
	}
}
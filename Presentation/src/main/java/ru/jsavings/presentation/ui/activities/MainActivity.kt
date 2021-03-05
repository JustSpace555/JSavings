package ru.jsavings.presentation.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.jsavings.R

class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		val navView: BottomNavigationView = findViewById(R.id.nav_view)

		val navController = findNavController(R.id.nav_host_fragment)
		// Passing each menu ID as a set of Ids because each
		// menu should be considered as top level destinations.
		val appBarConfiguration = AppBarConfiguration(
			setOf(
				R.id.transactions_fragment, R.id.calendar_fragment, R.id.graph_fragment, R.id.purses_fragment
			)
		)
		setupActionBarWithNavController(navController, appBarConfiguration)
		navView.setupWithNavController(navController)
		navView.background = null
		navView.menu.getItem(2).isEnabled = false
		supportActionBar?.hide()
	}
}
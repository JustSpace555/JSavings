package ru.jsavings.presentation.ui.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import ru.jsavings.R
import ru.jsavings.databinding.ActivityMainBinding
import ru.jsavings.presentation.di.presentationModule

/**
 * Main activity of JSavings app
 *
 * @author JustSpace
 */
class MainActivity : AppCompatActivity() {

	private lateinit var bindingUtil: ActivityMainBinding
	private lateinit var navController: NavController
	private val navigationListener = NavController.OnDestinationChangedListener { _, destination, _ ->
		with(bindingUtil) {

			when (destination.id) {
				R.id.transactions_fragment -> addTransactionFab.visibility = View.VISIBLE
				else -> addTransactionFab.visibility = View.GONE
			}

			when (destination.id) {
				R.id.transactions_fragment, R.id.calendar_fragment, R.id.graph_fragment, R.id.wallets_fragment -> {
					navView.visibility = View.VISIBLE
					bottomAppBar.visibility = View.VISIBLE
					topBar.visibility = View.VISIBLE
				}
				else -> {
					navView.visibility = View.GONE
					bottomAppBar.visibility = View.GONE
					topBar.visibility = View.GONE
				}
			}
		}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		startKoin {
			androidContext(this@MainActivity)
			loadKoinModules(presentationModule)
		}

		bindingUtil = ActivityMainBinding.inflate(layoutInflater)
		setContentView(bindingUtil.root)

		navController = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)!!.findNavController()

		val appBarConfiguration = AppBarConfiguration(
			setOf(
				R.id.transactions_fragment, R.id.calendar_fragment, R.id.graph_fragment, R.id.wallets_fragment
			)
		)

		setupActionBarWithNavController(navController, appBarConfiguration)

		bindingUtil.navView.apply {
			setupWithNavController(navController)
			background = null
		}

		supportActionBar?.hide()

		bindingUtil.addTransactionFab.setOnClickListener {
//			TODO navController.navigate()
		}
	}

	override fun onResume() {
		super.onResume()
		navController.addOnDestinationChangedListener(navigationListener)
	}

	override fun onPause() {
		super.onPause()
		navController.removeOnDestinationChangedListener(navigationListener)
	}

	override fun onDestroy() {
		super.onDestroy()
		stopKoin()
	}

	/**
	 * Set account name to top bar
	 * @param name Name of account
	 *
	 * @author Михаил Мошков
	 */
	fun setAccountName(name: String) {
		bindingUtil.materialTopBar.title = name
	}
}
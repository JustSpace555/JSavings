package ru.jsavings.presentation.ui.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import org.koin.android.viewmodel.ext.android.viewModel
import ru.jsavings.R
import ru.jsavings.databinding.ActivityMainBinding
import ru.jsavings.presentation.ui.fragments.transactions.alltransactions.TransactionsFragment
import ru.jsavings.presentation.ui.fragments.transactions.alltransactions.TransactionsFragmentDirections
import ru.jsavings.presentation.viewmodels.MainSharedViewModel

/**
 * Main activity of JSavings app
 *
 * @author JustSpace
 */
class MainActivity : AppCompatActivity() {

	private val viewModel by viewModel<MainSharedViewModel>()
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

		bindingUtil = ActivityMainBinding.inflate(layoutInflater)
		setContentView(bindingUtil.root)

		val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

		navController = navHostFragment.navController

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
			val fragment = navHostFragment.childFragmentManager.fragments.first()
			if (fragment is TransactionsFragment && fragment.isVisible) {
				navController.navigate(TransactionsFragmentDirections
					.actionTransactionsFragmentToNewTransactionFragment(viewModel.currentAccount.accountId)
				)
			}
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
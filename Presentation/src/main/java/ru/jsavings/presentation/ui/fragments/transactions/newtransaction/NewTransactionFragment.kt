package ru.jsavings.presentation.ui.fragments.transactions.newtransaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import org.koin.android.viewmodel.ext.android.viewModel
import ru.jsavings.R
import ru.jsavings.databinding.NewTransactionFragmentBinding
import ru.jsavings.domain.model.database.transaction.category.TransactionCategoryType
import ru.jsavings.domain.model.database.wallet.Wallet
import ru.jsavings.presentation.ui.fragments.categories.categorieslist.CategoriesListFragment
import ru.jsavings.presentation.ui.fragments.common.BaseFragment
import ru.jsavings.presentation.ui.fragments.common.BaseViewModel
import java.text.SimpleDateFormat
import java.util.*

/**
 * Fragment for adding new transaction
 *
 * @author Михаил Мошков
 */
class NewTransactionFragment : BaseFragment() {

	override val viewModel by viewModel<NewTransactionViewModel>()

	override lateinit var bindingUtil: NewTransactionFragmentBinding

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		bindingUtil = NewTransactionFragmentBinding.inflate(inflater, container, false)

		setFragmentResultListener(CategoriesListFragment.CATEGORY_CHOSEN) { _, bundle ->
			viewModel.transactionCategoryId = bundle.getLong(CategoriesListFragment.CHOSEN_CATEGORY_ID_KEY, -1L)
		}

		return bindingUtil.root
	}

	@Suppress("UNCHECKED_CAST")
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		hideKeyBoardOnRootTouchClick(bindingUtil.root)

		viewModel.requestAllWalletsState.observe(viewLifecycleOwner) { state ->
			when (state) {
				is BaseViewModel.RequestState.SuccessState<*> -> {
					viewModel.wallets.addAll(state.data as List<Wallet>)
					hideLoading()
				}
				is BaseViewModel.RequestState.ErrorState<*> -> {
					showTextSnackBar(state.t.getErrorString())
				}
				else -> showLoading(getString(R.string.loading_wallets))
			}
		}

		viewModel.requestAllWallets()
	}

	override fun onStart() {
		super.onStart()
		setUpDate()
		setUpTransactionType()
		setUpTransactionCategory()
		setUpWallets()
		setUpTransactionSum()
		setUpTransactionDescription()
	}

	private fun setUpDate() {
		val currentDate = Date(System.currentTimeMillis())
		bindingUtil.tilNewTransactionDateDay.editText?.setText(
			SimpleDateFormat("dd-MMM-yyyy", locale).format(currentDate)
		)
		bindingUtil.tilNewTransactionDateTime.editText?.setText(
			SimpleDateFormat("HH:mm:ss", locale).format(currentDate)
		)
	}

	private fun setUpTransactionType() {

		data class TransactionCategoryAdapterView(val type: TransactionCategoryType, val name: String) {
			override fun toString(): String = name
		}

		val listOfTypes = listOf(
			TransactionCategoryAdapterView(
				TransactionCategoryType.INCOME,
				getString(R.string.transaction_type_income)
			),
			TransactionCategoryAdapterView(
				TransactionCategoryType.CONSUMPTION,
				getString(R.string.transaction_type_consumption)
			),
			TransactionCategoryAdapterView(
				TransactionCategoryType.TRANSFER,
				getString(R.string.transaction_type_transfer)
			)
		)

		bindingUtil.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

			override fun onTabSelected(tab: TabLayout.Tab?) {
				tab?.let {
					when(it.id) {
						R.id.tab_income
					}
				}
			}

			override fun onTabUnselected(tab: TabLayout.Tab?) {}

			override fun onTabReselected(tab: TabLayout.Tab?) {}
		})
	}

	private fun setUpTransactionCategory() {
		bindingUtil.tilNewTransactionCategory.setOnClickListener {
			findNavController().navigate(
				NewTransactionFragmentDirections.actionNewTransactionFragmentToCategoriesListFragment()
			)
		}
	}

	private fun setUpWallets() {

		data class WalletsAdapterView(val id: Long, val name: String, val balance: Double, val symbol: String) {
			override fun toString(): String = "$name: $balance $symbol"
		}

		val listOfWalletsView = viewModel.wallets.map {
			WalletsAdapterView(it.walletId, it.name, it.balance, it.currency)
		}

		val adapter = ArrayAdapter(requireContext(), R.layout.item_dropdown_item, listOfWalletsView)

		bindingUtil.actvNewTransactionFromWallet.apply {
			setAdapter(adapter)
			setOnItemClickListener { _, _, position, _ ->
				viewModel.fromWalletId = listOfWalletsView[position].id
			}
		}

		bindingUtil.actvNewTransactionToWallet.apply {
			setAdapter(adapter)
			setOnItemClickListener { _, _, position, _ ->
				viewModel.toWalletId = listOfWalletsView[position].id
			}
		}
	}

	private fun setUpTransactionSum() {
		bindingUtil.tietNewTransactionSum.addTextChangedListener {
			it?.let { sumString -> viewModel.transactionSum = sumString.toString() }
		}
	}

	private fun setUpTransactionDescription() {
		bindingUtil.tietNewTransactionDescription.addTextChangedListener {
			it?.let { description -> viewModel.description = description.toString() }
		}
	}
}
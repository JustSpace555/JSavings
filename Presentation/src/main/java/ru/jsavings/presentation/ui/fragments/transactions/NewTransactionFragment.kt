package ru.jsavings.presentation.ui.fragments.transactions

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import org.koin.android.viewmodel.ext.android.viewModel
import ru.jsavings.R
import ru.jsavings.databinding.FragmentNewTransactionBinding
import ru.jsavings.domain.model.database.category.TransactionCategory
import ru.jsavings.domain.model.database.category.TransactionCategoryType
import ru.jsavings.domain.model.database.transaction.Transaction
import ru.jsavings.domain.model.database.wallet.Wallet
import ru.jsavings.presentation.extension.getCurrencySymbol
import ru.jsavings.presentation.extension.isNotEmptyAndNotBlank
import ru.jsavings.presentation.ui.fragments.categories.categorieslist.CategoriesListFragment
import ru.jsavings.presentation.ui.fragments.common.BaseFragment
import ru.jsavings.presentation.viewmodels.transactions.NewTransactionViewModel
import java.text.SimpleDateFormat
import java.util.*

/**
 * Fragment for adding new transaction
 *
 * @author Михаил Мошков
 */
class NewTransactionFragment : BaseFragment() {

	companion object {
		private const val DATE_PICKER_TAG = "DatePickerDialog"
		private const val TIME_PICKER_TAG = "TimePickerDialog"
	}

	private val args by navArgs<NewTransactionFragmentArgs>()

	override val viewModel by viewModel<NewTransactionViewModel>()
	private val bindingUtil get() = binding as FragmentNewTransactionBinding

	private lateinit var allWallets: List<Wallet>

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		binding = FragmentNewTransactionBinding.inflate(inflater, container, false)
		return bindingUtil.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		hideKeyBoardOnRootTouchClick()

		if (isInternetAvailable) {
			setUpObservers()
			viewModel.requestAllWallets(args.accountId)
		} else {
			findNavController().navigate(
				NewTransactionFragmentDirections.actionGlobalToNoInternetFragment()
			)
		}
	}

	private fun setUpObservers() {
		viewModel.requestAllWalletsState.subscribe<List<Wallet>>(
			hideLoading = true,
			onSuccess = {
				allWallets = it
				if (args.transactionId != -1L) {
					viewModel.requestTransactionByIdState(args.transactionId)
				} else {
					setUpUi()
				}
			},
			onError = { showTextSnackBar(
				text = it.getErrorString(),
				actionText = getString(R.string.retry),
				action = { viewModel.requestAllWallets(args.accountId) }
			)},
			onSending = { showLoading(R.string.loading_wallets) }
		)

		viewModel.requestSaveTransactionSate.subscribe<Any>(
			hideLoading = true,
			onSuccess = {
				if (args.transactionId != -1L) {
					findNavController().navigate(
						NewTransactionFragmentDirections.actionNewTransactionFragmentToTransactionsFragment()
					)
				} else {
					findNavController().popBackStack()
				}
			},
			onError = { showTextSnackBar(
				text = it.getErrorString(),
				actionText = getString(R.string.retry),
				action = { onSaveButtonClick() }
			)},
			onSending = { showLoading(R.string.loading_saving_transaction) }
		)

		requireActivity().supportFragmentManager
			.setFragmentResultListener(CategoriesListFragment.CATEGORY_CHOSEN, viewLifecycleOwner) { _, bundle ->
				val id = bundle.getLong(CategoriesListFragment.CHOSEN_CATEGORY_ID_KEY, -1L)
				viewModel.requestTransactionCategory(id)
			}

		viewModel.requestTransactionCategoryState.subscribe<TransactionCategory>(
			hideLoading = true,
			onSuccess = {
				viewModel.transactionCategory = it
				transactionTypeTabClick(it.categoryType)
				bindingUtil.tilNewTransactionCategory.isErrorEnabled = false
			},
			onError = { showTextSnackBar(text = it.getErrorString()) },
			onSending = { showLoading() }
		)

		viewModel.requestTransactionByIdState.subscribe<Transaction>(
			hideLoading = true,
			onSuccess = { setUpUi() },
			onError = {
				showTextSnackBar(it.getErrorString())
				setUpUi()
			},
			onSending = { showLoading(R.string.loading_getting_transaction) }
		)
	}

	private fun setUpUi() {
		hideKeyBoardOnRootTouchClick()
		setUpDate()
		setUpTransactionType()
		setUpTransactionCategory()
		setUpWallets()
		setUpTransactionSum()
		setUpTransactionDescription()
		setUpSaveButton()
	}

	@SuppressLint("SetTextI18n")
	private fun setUpDate() {
		val currentDateDay = Date(MaterialDatePicker.todayInUtcMilliseconds())
		val currentDateTime = Date(System.currentTimeMillis())

		fun Date.dateToString(pattern: String): String = SimpleDateFormat(pattern, locale).format(this)

		fun updateDateDay(date: Date) {
			viewModel.transactionDate = date.time
			bindingUtil.tietNewTransactionDateDay.setText(
				date.dateToString("yyyy MMM dd")
			)
		}

		fun updateDateTime(date: Date) {
			viewModel.transactionTime = date.time
			bindingUtil.tietNewTransactionDateTime.setText(
				date.dateToString("HH : mm")
			)
		}

		updateDateDay(if (viewModel.transactionDate != -1L) Date(viewModel.transactionDate) else currentDateDay)
		updateDateTime(if (viewModel.transactionTime != -1L) Date(viewModel.transactionTime) else currentDateTime)

		bindingUtil.tietNewTransactionDateDay.setOnClickListener {
			val datePicker = MaterialDatePicker.Builder.datePicker()
				.setTitleText(R.string.new_transaction_hint_day)
				.setSelection(currentDateDay.time)
				.build()
				.apply {
					addOnPositiveButtonClickListener {
						bindingUtil.tilNewTransactionDateDay.isErrorEnabled = false
						updateDateDay(Date(it))
					}
				}
			datePicker.show(requireActivity().supportFragmentManager, DATE_PICKER_TAG)
		}

		bindingUtil.tietNewTransactionDateTime.setOnClickListener {
			val cal = Calendar.getInstance(TimeZone.getDefault(), locale).apply {
				time = currentDateTime
			}

			val timePicker = MaterialTimePicker.Builder()
				.setTitleText(R.string.new_transaction_hint_time)
				.setTimeFormat(TimeFormat.CLOCK_24H)
				.setHour(cal[Calendar.HOUR_OF_DAY])
				.setMinute(cal[Calendar.MINUTE])
				.build()
				.apply {
					addOnPositiveButtonClickListener {
						bindingUtil.tilNewTransactionDateTime.isErrorEnabled = false
						cal.set(Calendar.HOUR_OF_DAY, hour)
						cal.set(Calendar.MINUTE, minute)
						updateDateTime(cal.time)
					}
				}
			timePicker.show(requireActivity().supportFragmentManager, TIME_PICKER_TAG)
		}
	}

	private fun setUpTransactionType() {

		bindingUtil.tabLayoutNewTransactionType.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

			override fun onTabSelected(tab: TabLayout.Tab?) {
				tab?.let {
					with(bindingUtil) {
						when (it.position) {
							0 -> {
								tilNewTransactionToWallet.isVisible = true
								tilNewTransactionFromWallet.isVisible = false
								bindingUtil.tilNewTransactionCategory.isVisible = true
								viewModel.transactionType = TransactionCategoryType.INCOME
							}
							1 -> {
								tilNewTransactionToWallet.isVisible = false
								tilNewTransactionFromWallet.isVisible = true
								tilNewTransactionCategory.isVisible = true
								viewModel.transactionType = TransactionCategoryType.CONSUMPTION
							}
							2 -> {
								tilNewTransactionFromWallet.isVisible = true
								tilNewTransactionToWallet.isVisible = true
								tilNewTransactionCategory.isVisible = false
								viewModel.transactionType = TransactionCategoryType.TRANSFER
							}
						}
					}
				}
			}

			override fun onTabUnselected(tab: TabLayout.Tab?) {
				viewModel.transactionCategory = null
				bindingUtil.actvNewTransactionCategory.setText("")
			}

			override fun onTabReselected(tab: TabLayout.Tab?) {}
		})

		if (viewModel.transactionType != TransactionCategoryType.INCOME) {
			transactionTypeTabClick(viewModel.transactionType)
		}
	}

	private fun setUpTransactionCategory() {
		viewModel.transactionCategory?.let { bindingUtil.actvNewTransactionCategory.setText(it.name) }

		bindingUtil.actvNewTransactionCategory.setOnClickListener {
			findNavController().navigate(
				NewTransactionFragmentDirections.actionNewTransactionFragmentToCategoriesListFragment(args.accountId)
			)
		}
	}

	private fun setUpWallets() {

		data class WalletsAdapterView(val wallet: Wallet, val symbol: String) {
			override fun toString(): String = "${wallet.name}: ${wallet.balance} $symbol"
		}

		val listOfWalletsView = allWallets.map {
			WalletsAdapterView(it, it.currency.getCurrencySymbol())
		}

		val adapter = ArrayAdapter(requireContext(), R.layout.item_dropdown_item, listOfWalletsView)

		viewModel.fromWallet?.let {
			val symbol = it.currency.getCurrencySymbol()
			bindingUtil.actvNewTransactionFromWallet.setText(
				WalletsAdapterView(it, symbol).toString()
			)
			bindingUtil.tilNewTransactionSum.suffixText = symbol
		}
		bindingUtil.actvNewTransactionFromWallet.apply {
			setAdapter(adapter)
			setOnItemClickListener { _, _, position, _ ->
				bindingUtil.tilNewTransactionFromWallet.isErrorEnabled = false
				viewModel.fromWallet = listOfWalletsView[position].wallet
				bindingUtil.tilNewTransactionSum.suffixText = listOfWalletsView[position].symbol
			}
		}

		viewModel.toWallet?.let {
			val symbol = it.currency.getCurrencySymbol()
			bindingUtil.actvNewTransactionToWallet.setText(WalletsAdapterView(it, symbol).toString())
			bindingUtil.tilNewTransactionSum.suffixText = symbol
		}
		bindingUtil.actvNewTransactionToWallet.apply {
			setAdapter(adapter)
			setOnItemClickListener { _, _, position, _ ->
				bindingUtil.tilNewTransactionToWallet.isErrorEnabled = false
				viewModel.toWallet = listOfWalletsView[position].wallet
				bindingUtil.tilNewTransactionSum.suffixText = listOfWalletsView[position].symbol
			}
		}
	}

	private fun setUpTransactionSum() {
		if (viewModel.transactionSum.isNotEmptyAndNotBlank()) {
			bindingUtil.tietNewTransactionSum.setText(viewModel.transactionSum)
		}
		bindingUtil.tietNewTransactionSum.addTextChangedListener {
			it?.let { sumString ->
				bindingUtil.tilNewTransactionSum.isErrorEnabled = false
				viewModel.transactionSum = sumString.toString()
			}
		}
	}

	private fun setUpTransactionDescription() {
		if (viewModel.transactionDescription.isNotEmptyAndNotBlank()) {
			bindingUtil.tietNewTransactionDescription.setText(viewModel.transactionDescription)
		}
		bindingUtil.tietNewTransactionDescription.addTextChangedListener {
			it?.let { description -> viewModel.transactionDescription = description.toString() }
		}
	}

	private fun setUpSaveButton() {
		if (args.transactionId != -1L) bindingUtil.buttonNewTransactionSave.setText(R.string.update_transaction)
		bindingUtil.buttonNewTransactionSave.setOnClickListener { onSaveButtonClick() }
	}

	private fun transactionTypeTabClick(transactionCategoryType: TransactionCategoryType?) {
		val oldCategory = viewModel.transactionCategory
		when(transactionCategoryType) {
			TransactionCategoryType.INCOME -> bindingUtil.tabLayoutNewTransactionType.getTabAt(0)
			TransactionCategoryType.CONSUMPTION -> bindingUtil.tabLayoutNewTransactionType.getTabAt(1)
			else -> bindingUtil.tabLayoutNewTransactionType.getTabAt(2)
		}?.select()
		viewModel.transactionCategory = oldCategory
		viewModel.transactionCategory?.let { bindingUtil.actvNewTransactionCategory.setText(it.name) }
	}

	private fun onSaveButtonClick() {
		if (args.transactionId != -1L) {
			showLoading(R.string.loading_updating_transaction)
		} else {
			showLoading(getString(R.string.loading_saving_transaction))
		}

		val validateResult = viewModel.validateInputData()
		if (validateResult != NewTransactionViewModel.DATA_VALID) {

			fun TextInputLayout.checkAndShowError(errorCode: Int, errorStringId: Int) {
				if (viewModel.getValidationResult(validateResult to errorCode)) error = getString(errorStringId)
			}

			with(bindingUtil) {
				tilNewTransactionDateDay.checkAndShowError(
					NewTransactionViewModel.TIME_INVALID, R.string.new_transaction_error_future_time
				)
				tilNewTransactionDateTime.checkAndShowError(
					NewTransactionViewModel.TIME_INVALID, R.string.new_transaction_error_future_time
				)
				tilNewTransactionCategory.checkAndShowError(
					NewTransactionViewModel.CATEGORY_EMPTY, R.string.new_transaction_error_category_empty
				)
				tilNewTransactionFromWallet.checkAndShowError(
					NewTransactionViewModel.FROM_WALLET_EMPTY, R.string.new_transaction_error_wallet_empty
				)
				tilNewTransactionToWallet.checkAndShowError(
					NewTransactionViewModel.TO_WALLET_EMPTY, R.string.new_transaction_error_wallet_empty
				)
				tilNewTransactionSum.checkAndShowError(
					NewTransactionViewModel.TRANSACTION_SUM_EMPTY, R.string.new_transaction_error_sum_empty
				)
				tilNewTransactionSum.checkAndShowError(
					NewTransactionViewModel.TRANSACTION_SUM_INVALID, R.string.new_transaction_error_sum_invalid
				)
				tilNewTransactionSum.checkAndShowError(
					NewTransactionViewModel.TRANSACTION_SUM_HIGHER, R.string.new_transaction_error_low_balance
				)
			}
			hideLoading()
			return
		}

		if (!isInternetAvailable) {
			hideLoading()
			findNavController().navigate(NewTransactionFragmentDirections.actionGlobalToNoInternetFragment())
			return
		}

		if (args.transactionId != -1L)
			viewModel.requestUpdateTransaction(args.accountId, args.transactionId)
		else
			viewModel.requestSaveTransaction(args.accountId)
	}
}
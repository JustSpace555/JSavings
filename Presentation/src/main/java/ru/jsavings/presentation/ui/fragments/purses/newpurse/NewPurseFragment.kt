package ru.jsavings.presentation.ui.fragments.purses.newpurse

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.pes.androidmaterialcolorpickerdialog.ColorPicker
import org.koin.android.viewmodel.ext.android.viewModel
import ru.jsavings.R
import ru.jsavings.data.model.database.Account
import ru.jsavings.data.model.database.purse.Purse
import ru.jsavings.data.model.database.purse.PurseCategoryType
import ru.jsavings.data.model.network.common.BaseNetworkModel
import ru.jsavings.data.model.network.crypto.CryptoCoin
import ru.jsavings.data.model.network.currency.Currency
import ru.jsavings.data.repository.cache.CacheKeys
import ru.jsavings.databinding.PurseFragmentNewPurseBinding
import ru.jsavings.presentation.ui.fragments.common.BaseFragment
import ru.jsavings.presentation.ui.fragments.common.BaseViewModel

class NewPurseFragment : BaseFragment() {

	override val viewModel by viewModel<NewPurseViewModel>()

	override lateinit var bindingUtil: PurseFragmentNewPurseBinding

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View {
		bindingUtil = PurseFragmentNewPurseBinding.inflate(inflater, container, false)
		return bindingUtil.root
	}

	@SuppressLint("ClickableViewAccessibility")
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		if (viewModel.purseColor == 0) {
			viewModel.purseColor = TypedValue().apply {
				requireActivity().theme.resolveAttribute(R.attr.colorPrimary, this, true)
			}.data
		}

		with(bindingUtil) {

			buttonNewPurseChooseColor.setBackgroundColor(viewModel.purseColor)

			setCreditPurseVisibility(View.GONE)
			tilNewPurseCurrency.isEnabled = false

			hideKeyBoardOnRootTouch(root)

			requestCurrencies()
			requestCryptoCoins()

			tilNewPurseName.editText?.addTextChangedListener { tilNewPurseName.error = null }
			tilNewPurseType.editText?.addTextChangedListener { tilNewPurseType.error = null }
			tilNewPurseStartingBalance.editText?.addTextChangedListener {
				tilNewPurseStartingBalance.error = null
			}
			tilNewPurseCurrency.editText?.addTextChangedListener {
				tilNewPurseCurrency.error = null
			}

			buttonNewPurseChooseColor.setOnClickListener(::setOnChoosePurseButtonColorClickListener)

			/* TODO Credit card
			tilNewPurseCreditPercent.editText?.doOnTextChanged { text, _, _, _ ->
				text?.let { onCreditPersentTextChanged(it.toString()) }
			}
			*/

			buttonNewPurseSave.setOnClickListener { onSaveButtonClickListener() }
		}
	}

	override fun onStart() {
		super.onStart()

		with(bindingUtil) {
			val purseTypeAdapter = setupPurseTypeAdapter().also { actvNewPurseType.setAdapter(it) }
			actvNewPurseType.setOnItemClickListener { _, _, i, _ ->
				tilNewPurseCurrency.isEnabled = true
				purseTypeAdapter.getItem(i)?.let { purseType ->
					viewModel.purseType = purseType.type
					actvNewPurseCurrency.setAdapter(setupCurrencyAdapter(purseType.type))
				}
			}
		}
	}

	private data class PurseTypeObject(val type: PurseCategoryType, val name: String) {
		override fun toString() = name
	}
	private fun setupPurseTypeAdapter(): ArrayAdapter<PurseTypeObject> {

		val list = PurseCategoryType.values().map {

			val strId = when (it) {
				PurseCategoryType.CASH -> R.string.purse_type_cash
				PurseCategoryType.CREDIT_CARD -> R.string.purse_type_credit_card
				PurseCategoryType.DEBIT_CARD -> R.string.purse_type_debit_card
				PurseCategoryType.CRYPTO_CURRENCY -> R.string.purse_type_crypto_currency
				PurseCategoryType.PRECIOUS_METALS -> R.string.purse_type_precious_metal
				PurseCategoryType.SECURITIES -> R.string.purse_type_securities
			}

			PurseTypeObject(type = it, name = getString(strId))
		}

		return ArrayAdapter(requireContext(), R.layout.item_dropdown_item, list)
	}

	@Suppress("UNCHECKED_CAST")
	private fun setupCurrencyAdapter(purseType: PurseCategoryType): ArrayAdapter<BaseNetworkModel> {

		val adapter = ArrayAdapter<BaseNetworkModel>(requireContext(), R.layout.item_dropdown_item)

		return when (purseType) {

			//TODO API
			PurseCategoryType.PRECIOUS_METALS, PurseCategoryType.SECURITIES -> {
				setCreditPurseVisibility(View.GONE)
				adapter
			}

			PurseCategoryType.CRYPTO_CURRENCY -> {
				setCreditPurseVisibility(View.GONE)

				viewModel.cryptoCoinRequestState.observe(viewLifecycleOwner) { state ->
					when (state) {
						is BaseViewModel.RequestState.SuccessState<*> -> {
							viewModel.currencyList.clear()
							state.data as List<CryptoCoin>
							viewModel.currencyList.addAll(state.data.map { it.id.uppercase() })
							adapter.addAll(state.data)
						}
						is BaseViewModel.RequestState.ErrorState<*> -> view?.let {
							showTextSnackBar(
								text = state.t.getErrorString(),
								actionText = getString(R.string.retry),
								action = { requestCryptoCoins() }
							)
						}
						else -> {}
					}
				}
				adapter
			}

			else -> {
				setCreditPurseVisibility(
					if (purseType == PurseCategoryType.CREDIT_CARD)
						View.VISIBLE
					else
						View.GONE
				)
				viewModel.currenciesRequestState.observe(viewLifecycleOwner) { state ->
					when (state) {
						is BaseViewModel.RequestState.SuccessState<*> -> {
							viewModel.currencyList.clear()
							state.data as List<Currency>
							viewModel.currencyList.addAll(state.data.map { it.code })
							adapter.addAll(state.data)
						}
						is BaseViewModel.RequestState.ErrorState<*> -> view?.let {
							showTextSnackBar(
								text = state.t.getErrorString(),
								actionText = getString(R.string.retry),
								action = { requestCurrencies() }
							)
						}
						else -> {}
					}
				}
				adapter
			}
		}
	}

	private fun setOnChoosePurseButtonColorClickListener(view: View) {
		ColorPicker(
			requireActivity(),
			Color.red(viewModel.purseColor),
			Color.green(viewModel.purseColor),
			Color.blue(viewModel.purseColor)
		).apply {
			setCallback { color ->
				viewModel.purseColor = color
				view.setBackgroundColor(color)
			}
			enableAutoClose()
			show()
		}
	}

	private fun onSaveButtonClickListener() {
		showLoading()
		if (!validateAllEditTexts()) {
			hideLoading()
			return
		}

		val newPurseName = bindingUtil.tietNewPurseName.text?.toString()
		if (newPurseName == null) {
			hideLoading()
			bindingUtil.tietNewPurseName.error = getString(R.string.new_purse_error_name_empty)
			return
		}

		val newPurseStartingBalanceText = bindingUtil.tietNewPurseStartingBalance.text.toString()
		var newPurseStartingBalance = if (newPurseStartingBalanceText.isEmpty())
			0.0
		else
			newPurseStartingBalanceText.toDouble()

		val args by navArgs<NewPurseFragmentArgs>()
		val newAccount = Account(
			accountId = viewModel.getFromCache(CacheKeys.JS_CURRENT_ACCOUNT, 0L),
			name = args.newAccountName,
			mainCurrencyCode = args.newAccountMainCurrency,
			balanceInMainCurrency = newPurseStartingBalance
		)

		val newPurse = Purse(
			name = newPurseName,
			balance = newPurseStartingBalance,
			currency = bindingUtil.actvNewPurseCurrency.text.toString().split(' ').first(),
			category = viewModel.purseType,
			account = newAccount,
			color = viewModel.purseColor,
			//TODO
			iconPath = ""
		)

		addOrUpdateAccountAndSavePurse(newAccount, newPurse)
	}

	private fun setCreditPurseVisibility(visibility: Int) {
		bindingUtil.hsvCreditPurseElements.visibility = visibility
	}

	private fun validateAllEditTexts(): Boolean = with(bindingUtil) {
		val nameText = tietNewPurseName.text

		val isNameValid = !(nameText.isNullOrEmpty() || nameText.isNullOrBlank())
		if (!isNameValid)
			tietNewPurseName.error = getString(R.string.new_purse_error_name_empty)

		val typeText = actvNewPurseType.text.toString()
		val isTypeValid = typeText.isNotEmpty() && typeText.isNotBlank()
		if (!isTypeValid) tilNewPurseType.error = getString(R.string.new_purse_error_type_empty)

		val currencyText = actvNewPurseCurrency.text.toString()
		var isCurrencyValid = currencyText.isNotEmpty() && currencyText.isNotBlank()
		if (!isCurrencyValid)
			tilNewPurseCurrency.error = getString(R.string.new_purse_error_currency_empty)

		val purseCurrencyId = actvNewPurseCurrency.text.toString().split(' ').first()
		if (isTypeValid && isCurrencyValid && !viewModel.currencyList.contains(purseCurrencyId)) {
			isCurrencyValid = false
			tilNewPurseCurrency.error = getString(R.string.new_purse_error_currency_wrong)
		}

		val startingBalanceText = tilNewPurseStartingBalance.editText?.text?.toString()
		val isStartingBalanceValid = when {
			startingBalanceText == null -> false
			startingBalanceText.isEmpty() -> true
			startingBalanceText.isBlank() -> false
			else -> {
				var isValid = true
				try {
					startingBalanceText.toDouble()
				} catch (e: NumberFormatException) {
					isValid = false
				}
				isValid
			}
		}
		if (!isStartingBalanceValid)
			tilNewPurseStartingBalance.error =
				getString(R.string.new_purse_error_starting_balance_wrong)

		//TODO api

		isNameValid && isTypeValid && isCurrencyValid && isStartingBalanceValid
	}

	private fun requestCurrencies() {
		if (!isInternetAvailable)
			findNavController().navigate(R.id.action_global_to_no_internet_fragment)
		else
			viewModel.requestCurrencies()
	}

	private fun requestCryptoCoins() {
		if (!isInternetAvailable)
			findNavController().navigate(R.id.action_global_to_no_internet_fragment)
		else
			viewModel.requestCryptoCoins()
	}

	private fun addOrUpdateAccountAndSavePurse(account: Account, purse: Purse) {

		viewModel.requestAddOrUpdateAccountAndSavePurse(account, purse)

		viewModel.addOrUpdateAccountAndSavePurseRequest.observe(viewLifecycleOwner) { state ->
			when (state) {
				is BaseViewModel.RequestState.SuccessState<*> -> {
					hideLoading()
					if (viewModel.getFromCache(CacheKeys.JS_CURRENT_ACCOUNT, -1L) != -1L) {
						findNavController().popBackStack()
					} else {
						val newAccountId = state.data as Long
						viewModel.putToCache(CacheKeys.JS_CURRENT_ACCOUNT, newAccountId)
						val action = if (navArgs<NewPurseFragmentArgs>().value.isEducationNeeded)
							NewPurseFragmentDirections.actionNewPurseFragmentToReadyFragment(
								newAccountId
							)
						else
							NewPurseFragmentDirections.actionGlobalFragmentToTransactionsFragment(
								newAccountId
							)
						findNavController().navigate(action)
					}
				}
				is BaseViewModel.RequestState.ErrorState<*> -> {
					hideLoading()
					showTextSnackBar(
						text = state.t.getErrorString(),
						actionText = getString(R.string.retry),
						action = { addOrUpdateAccountAndSavePurse(account, purse) }
					)
				}
				else -> {}
			}
		}
	}
}
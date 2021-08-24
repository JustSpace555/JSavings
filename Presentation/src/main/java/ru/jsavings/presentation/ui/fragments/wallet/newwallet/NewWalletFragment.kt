package ru.jsavings.presentation.ui.fragments.wallet.newwallet

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.TextInputLayout
import com.pes.androidmaterialcolorpickerdialog.ColorPicker
import org.koin.android.viewmodel.ext.android.viewModel
import ru.jsavings.R
import ru.jsavings.databinding.WalletFragmentNewWalletBinding
import ru.jsavings.domain.model.database.wallet.WalletCategoryType
import ru.jsavings.domain.model.network.crypto.CryptoCoin
import ru.jsavings.domain.model.network.currency.Currency
import ru.jsavings.presentation.ui.fragments.common.BaseFragment
import ru.jsavings.presentation.ui.fragments.common.BaseViewModel

/**
 * Create new wallet fragment
 *
 * @author JustSpace
 */
class NewWalletFragment : BaseFragment() {

	override val viewModel by viewModel<NewWalletViewModel>()

	override lateinit var bindingUtil: WalletFragmentNewWalletBinding

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View {
		bindingUtil = WalletFragmentNewWalletBinding.inflate(inflater, container, false)
		return bindingUtil.root
	}

	@SuppressLint("ClickableViewAccessibility")
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		if (viewModel.walletColor == 0) {
			viewModel.walletColor = TypedValue().apply {
				requireActivity().theme.resolveAttribute(R.attr.colorPrimary, this, true)
			}.data
		}

		with(bindingUtil) {

			buttonNewWalletChooseColor.setBackgroundColor(viewModel.walletColor)

			setCreditWalletVisibility(View.GONE)
			tilNewWalletCurrency.isEnabled = viewModel.walletCurrency.isNotEmpty()

			hideKeyBoardOnRootTouchClick(root)

			requestCurrencies()

			tilNewWalletName.editText?.addTextChangedListener {
				tilNewWalletName.error = null
				it?.toString()?.let { name -> viewModel.walletName = name }
			}
			tilNewWalletType.editText?.addTextChangedListener { tilNewWalletType.error = null }
			tilNewWalletStartingBalance.editText?.addTextChangedListener {
				tilNewWalletStartingBalance.error = null
				it?.toString()?.let { balanceText -> viewModel.walletStartingBalance = balanceText }
			}
			tilNewWalletCurrency.editText?.addTextChangedListener {
				tilNewWalletCurrency.error = null
				it?.toString()?.let { currencyText -> viewModel.walletCurrency = currencyText }
			}

			buttonNewWalletChooseColor.setOnClickListener(::setOnChooseWalletButtonColorClickListener)

			/* TODO Credit card
			tilNewwalletCreditPercent.editText?.doOnTextChanged { text, _, _, _ ->
				text?.let { onCreditPersentTextChanged(it.toString()) }
			}
			*/

			buttonNewWalletSave.setOnClickListener { onSaveButtonClick() }
		}
	}

	@Suppress("UNCHECKED_CAST")
	override fun onStart() {
		super.onStart()

		viewModel.currenciesRequestState.observe(viewLifecycleOwner) { state ->
			when (state) {
				is BaseViewModel.RequestState.SuccessState<*> -> {
					state.data as List<Currency>
					viewModel.currencyList.addAll(
						state.data.map { it.toString() }
					)
					requestCryptoCoins()
				}
				is BaseViewModel.RequestState.ErrorState<*> -> {
					hideLoading()
					showTextSnackBar(
						text = state.t.getErrorString(),
						actionText = getString(R.string.retry),
						action = { requestCurrencies() }
					)
				}
				else -> showLoading(getString(R.string.loading_getting_currencies))
			}
		}

		viewModel.cryptoCoinRequestState.observe(viewLifecycleOwner) { state ->
			when (state) {
				is BaseViewModel.RequestState.SuccessState<*> -> {
					state.data as List<CryptoCoin>
					hideLoading()
					viewModel.cryptoCoinsList.addAll(
						state.data.map { it.toString() }
					)
				}
				is BaseViewModel.RequestState.ErrorState<*> -> {
					hideLoading()
					showTextSnackBar(
						text = state.t.getErrorString(),
						actionText = getString(R.string.retry),
						action = { requestCryptoCoins() }
					)
				}
				else -> setLoadingText(getString(R.string.loading_getting_crypto_coins))
			}
		}

		with(bindingUtil) {
			val typeAdapter = setupWalletTypeAdapter()
			actvNewWalletType.setAdapter(typeAdapter)
			actvNewWalletType.setOnItemClickListener { _, _, i, _ ->
				tilNewWalletCurrency.isEnabled = true
				typeAdapter.getItem(i)?.let { walletType ->

					viewModel.chosenCurrencyList = when(walletType.type) {
						WalletCategoryType.CASH, WalletCategoryType.CREDIT_CARD, WalletCategoryType.DEBIT_CARD ->
							viewModel.currencyList
						WalletCategoryType.CRYPTO_CURRENCY -> viewModel.cryptoCoinsList
						else -> emptyList() //TODO api
					}

					viewModel.walletType = walletType.type.toString()
					actvNewWalletCurrency.setAdapter(setupCurrencyAdapter(walletType.type))
				}
			}
		}
	}

	private data class WalletTypeObject(val type: WalletCategoryType, val name: String) {
		override fun toString() = name
	}

	private fun setupWalletTypeAdapter(): ArrayAdapter<WalletTypeObject> {

		val list = WalletCategoryType.values().map {

			val strId = when (it) {
				WalletCategoryType.CASH -> R.string.wallet_type_cash
				WalletCategoryType.CREDIT_CARD -> R.string.wallet_type_credit_card
				WalletCategoryType.DEBIT_CARD -> R.string.wallet_type_debit_card
				WalletCategoryType.CRYPTO_CURRENCY -> R.string.wallet_type_crypto_currency
				WalletCategoryType.PRECIOUS_METALS -> R.string.wallet_type_precious_metal
				WalletCategoryType.SECURITIES -> R.string.wallet_type_securities
			}

			WalletTypeObject(type = it, name = getString(strId))
		}

		return ArrayAdapter(requireContext(), R.layout.item_dropdown_item, list)
	}

	private val currencyAdapter by lazy { ArrayAdapter<String>(requireContext(), R.layout.item_dropdown_item) }

	@Suppress("UNCHECKED_CAST")
	private fun setupCurrencyAdapter(walletType: WalletCategoryType): ArrayAdapter<String> = when (walletType) {

		//TODO API
		WalletCategoryType.PRECIOUS_METALS, WalletCategoryType.SECURITIES -> {
			setCreditWalletVisibility(View.GONE)
			currencyAdapter
		}

		WalletCategoryType.CRYPTO_CURRENCY -> {
			setCreditWalletVisibility(View.GONE)
			currencyAdapter.apply {
				clear()
				addAll(viewModel.cryptoCoinsList)
			}
		}

		else -> {
			setCreditWalletVisibility(
				if (walletType == WalletCategoryType.CREDIT_CARD)
					View.VISIBLE
				else
					View.GONE
			)
			currencyAdapter.apply {
				clear()
				addAll(viewModel.currencyList)
			}
		}
	}

	private fun setOnChooseWalletButtonColorClickListener(view: View) {
		ColorPicker(
			requireActivity(),
			Color.red(viewModel.walletColor),
			Color.green(viewModel.walletColor),
			Color.blue(viewModel.walletColor)
		).apply {
			setCallback { color ->
				viewModel.walletColor = color
				view.setBackgroundColor(color)
				bindingUtil.buttonNewWalletChooseColor.setTextColor(
					if (color.red >= 200 && color.green >= 200 && color.blue >= 200)
						resources.getColor(R.color.black, requireContext().theme)
					else
						resources.getColor(R.color.white, requireContext().theme)
				)
			}
			enableAutoClose()
			show()
		}
	}

	private fun onSaveButtonClick() {
		showLoading(getString(R.string.loading_saving_wallet))

		val validationResult = viewModel.validateInputData()
		if (validationResult != NewWalletViewModel.VALID_CHECK) {
			with(bindingUtil) {

				fun TextInputLayout.checkAndShowError(checkCode: Int, stringId: Int) {
					if (viewModel.getValidationResult(validationResult, checkCode)) {
						error = getString(stringId)
						isErrorEnabled = true
					}
				}

				tilNewWalletName.checkAndShowError(
					NewWalletViewModel.INVALID_WALLET_NAME, R.string.new_wallet_error_name_empty
				)
				tilNewWalletType.checkAndShowError(
					NewWalletViewModel.INVALID_WALLET_TYPE_EMPTY, R.string.new_wallet_error_type_empty
				)
				tilNewWalletCurrency.checkAndShowError(
					NewWalletViewModel.INVALID_WALLET_CURRENCY_EMPTY, R.string.new_wallet_error_currency_empty
				)
				tilNewWalletCurrency.checkAndShowError(
					NewWalletViewModel.INVALID_WALLET_CURRENCY, R.string.new_wallet_error_currency_wrong
				)
				tilNewWalletStartingBalance.checkAndShowError(
					NewWalletViewModel.INVALID_WALLET_BALANCE, R.string.new_wallet_error_starting_balance_wrong
				)

			}
			hideLoading()
			return
		}

		viewModel.saveWalletRequestState.observe(viewLifecycleOwner) { state ->
			when(state) {
				is BaseViewModel.RequestState.SuccessState<*> -> {
					hideLoading()
					val args by navArgs<NewWalletFragmentArgs>()
					if (args.isEducationNeeded)
						findNavController().navigate(
							NewWalletFragmentDirections.actionNewWalletFragmentToReadyFragment()
						)
					else
						findNavController().popBackStack()
				}
				is BaseViewModel.RequestState.ErrorState<*> -> {
					hideLoading()
					showTextSnackBar(
						text = state.t.getErrorString(),
						actionText = getString(R.string.retry),
						action = { onSaveButtonClick() }
					)
				}
				else -> setLoadingText(getString(R.string.loading_saving_wallet))
			}
		}
		viewModel.requestSaveWallet()
	}

	private fun setCreditWalletVisibility(visibility: Int) {
		bindingUtil.hsvCreditWalletElements.visibility = visibility
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
}
package ru.jsavings.presentation.ui.fragments.wallets

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.TextInputLayout
import com.pes.androidmaterialcolorpickerdialog.ColorPicker
import org.koin.android.viewmodel.ext.android.viewModel
import ru.jsavings.R
import ru.jsavings.databinding.FragmentNewWalletBinding
import ru.jsavings.domain.model.database.wallet.WalletType
import ru.jsavings.domain.model.network.common.BaseCurrency
import ru.jsavings.domain.model.network.crypto.CryptoCoin
import ru.jsavings.domain.model.network.currency.Currency
import ru.jsavings.presentation.extension.isTextBlack
import ru.jsavings.presentation.ui.fragments.common.BaseFragment
import ru.jsavings.presentation.viewmodels.wallets.NewWalletViewModel

/**
 * Create new wallet fragment
 *
 * @author JustSpace
 */
class NewWalletFragment : BaseFragment() {

	override val viewModel by viewModel<NewWalletViewModel>()
	override lateinit var bindingUtil: FragmentNewWalletBinding

	private lateinit var currenciesList: List<Currency>
	private lateinit var cryptoCoinsList: List<CryptoCoin>
	private val chosenCurrencyList = mutableListOf<BaseCurrency>()

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View {
		bindingUtil = FragmentNewWalletBinding.inflate(inflater, container, false)
		return bindingUtil.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setUpObservers()
		setUpUi()
		viewModel.requestCurrencies()
	}

	private fun setUpUi() {

		if (viewModel.walletColor == 0) {
			viewModel.walletColor = TypedValue().apply {
				requireActivity().theme.resolveAttribute(R.attr.colorPrimary, this, true)
			}.data
		}

		with(bindingUtil) {
			hideKeyBoardOnRootTouchClick()

			// Name
			tilNewWalletName.editText?.let {
				it.addTextChangedListener { editable ->
					editable?.toString()?.let { name ->
						tilNewWalletName.isErrorEnabled = false
						tilNewWalletType.isEnabled = true
						viewModel.walletName = name
					}
				}
				if (viewModel.walletName.isNotEmpty() && viewModel.walletName.isNotBlank())
					it.setText(viewModel.walletName)
			}

			// Type
			tilNewWalletType.editText?.addTextChangedListener { tilNewWalletType.isErrorEnabled = false }

			actvNewWalletType.apply {
				setAdapter(setupWalletTypeAdapter())
				setOnItemClickListener { parent, _, i, _ -> (parent.getItemAtPosition(i) as? WalletTypeObject)?.let {
					viewModel.walletType = it.type.toString()
					tilNewWalletCurrency.isEnabled = true

					hsvCreditWalletElements.isVisible = it.type == WalletType.CREDIT_CARD

					chosenCurrencyList.clear()
					chosenCurrencyList.addAll(when(it.type) {
						WalletType.CASH, WalletType.DEBIT_CARD, WalletType.CREDIT_CARD -> currenciesList
						WalletType.CRYPTO_CURRENCY -> cryptoCoinsList
						else -> emptyList()
					})
				}}
			}

			// Currency
			tilNewWalletCurrency.editText?.addTextChangedListener { it?.toString()?.let { currency ->
				tilNewWalletCurrency.isErrorEnabled = false
				viewModel.walletCurrency = currency
			}}

			actvNewWalletCurrency.apply {
				setAdapter(ArrayAdapter(requireContext(), R.layout.item_dropdown_item, chosenCurrencyList))
				setOnItemClickListener { parent, _, i, _ ->
					val item = parent.getItemAtPosition(i).toString()
					viewModel.walletCurrency = item
					tilNewWalletStartingBalance.apply {
						suffixText = item.split(' ').last()
						isEnabled = true
					}
				}
			}

			// Starting balance
			tilNewWalletStartingBalance.editText?.addTextChangedListener { it?.toString()?.let { balance ->
				tilNewWalletStartingBalance.isErrorEnabled = false
				viewModel.walletStartingBalance = balance
			}}

			// TODO Credit purse info

			// Choose color button
			buttonNewWalletChooseColor.apply {
				setBackgroundColor(viewModel.walletColor)
				setOnClickListener(::setOnChooseWalletButtonColorClickListener)
			}

			// Save button
			buttonNewWalletSave.setOnClickListener { onSaveButtonClick() }
		}
	}

	private fun setUpObservers() {
		viewModel.currenciesRequestState.subscribe<List<Currency>>(
			hideLoading = false,
			onSuccess = {
				currenciesList = it
				requestCryptoCoins()
			},
			onError = { showTextSnackBar(
				text = it.getErrorString(),
				actionText = getString(R.string.retry),
				action = { requestCurrencies() }
			)},
			onSending = { showLoading(R.string.loading_getting_currencies) }
		)

		viewModel.cryptoCoinRequestState.subscribe<List<CryptoCoin>>(
			hideLoading = true, //TODO api
			onSuccess = { cryptoCoinsList = it },
			onError = { showTextSnackBar(
				text = it.getErrorString(),
				actionText = getString(R.string.retry),
				action = { requestCryptoCoins() }
			)},
			onSending = { showLoading(R.string.loading_getting_crypto_coins) }
		)

		viewModel.saveWalletRequestState.subscribe<Long>(
			hideLoading = true,
			onSuccess = {
				val args by navArgs<NewWalletFragmentArgs>()
				if (args.isEducationNeeded)
					findNavController().navigate(
						NewWalletFragmentDirections.actionNewWalletFragmentToReadyFragment()
					)
				else
					findNavController().popBackStack()
			},
			onError = { showTextSnackBar(
				text = it.getErrorString(),
				actionText = getString(R.string.retry),
				action = { onSaveButtonClick() }
			)},
			onSending = { showLoading(R.string.loading_saving_wallet) }
		)
	}

	private data class WalletTypeObject(val type: WalletType, val name: String) {
		override fun toString() = name
	}

	private fun setupWalletTypeAdapter(): ArrayAdapter<WalletTypeObject> {

		val list = WalletType.values().map {

			val strId = when (it) {
				WalletType.CASH -> R.string.wallet_type_cash
				WalletType.CREDIT_CARD -> R.string.wallet_type_credit_card
				WalletType.DEBIT_CARD -> R.string.wallet_type_debit_card
				WalletType.CRYPTO_CURRENCY -> R.string.wallet_type_crypto_currency
				WalletType.PRECIOUS_METALS -> R.string.wallet_type_precious_metal
				WalletType.SECURITIES -> R.string.wallet_type_securities
			}

			WalletTypeObject(type = it, name = getString(strId))
		}

		return ArrayAdapter(requireContext(), R.layout.item_dropdown_item, list)
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
				bindingUtil.buttonNewWalletChooseColor.setTextColor(AppCompatResources.getColorStateList(
					requireContext(),
					if (color.isTextBlack()) R.color.black else R.color.white
				))
			}
			enableAutoClose()
			show()
		}
	}

	private fun onSaveButtonClick() {
		showLoading(getString(R.string.loading_saving_wallet))

		val validationResult = viewModel.validateInputData(chosenCurrencyList.map { it.toString() })

		fun TextInputLayout.checkAndShowError(checkCode: Int, stringId: Int) {
			if (viewModel.getValidationResult(validationResult, checkCode)) error = getString(stringId)
		}

		if (validationResult != NewWalletViewModel.VALID_CHECK) {
			with(bindingUtil) {

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
					NewWalletViewModel.INVALID_WALLET_BALANCE, R.string.new_wallet_error_starting_balance_invalid
				)

			}
			hideLoading()
			return
		}

		viewModel.requestSaveWallet()
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
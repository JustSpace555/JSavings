package ru.jsavings.presentation.ui.fragments.purses.newpurse

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.ColorInt
import com.google.android.material.textfield.TextInputLayout
import com.pes.androidmaterialcolorpickerdialog.ColorPicker
import org.koin.android.viewmodel.ext.android.viewModel
import ru.jsavings.R
import ru.jsavings.data.model.database.Account
import ru.jsavings.data.model.database.purse.PurseCategoryType
import ru.jsavings.databinding.PurseFragmentNewPurseBinding
import ru.jsavings.presentation.ui.fragments.common.BaseFragment
import ru.jsavings.presentation.ui.fragments.common.BaseViewModel
import java.util.*

class NewPurseFragment : BaseFragment() {

	override val viewModel by viewModel<NewPurseViewModel>()

	override lateinit var bindingUtil: PurseFragmentNewPurseBinding

	@ColorInt
	private var currentColor: Int = Color.BLUE

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		bindingUtil = PurseFragmentNewPurseBinding.inflate(inflater, container, false)
		return bindingUtil.root
	}

	@SuppressLint("ClickableViewAccessibility")
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		currentColor = TypedValue().apply {
			requireActivity().theme.resolveAttribute(R.attr.colorPrimary, this, true)
		}.data

		with(bindingUtil) {

			setCreditPurseVisibility(View.GONE)
			tilNewPurseCurrency.isEnabled = false

			hideKeyBoardOnRootTouch(root)

			viewModel.requestCryptoCoins { t ->
				showTextSnackBar(
					view,
					t.localizedMessage ?: getString(R.string.something_went_wrong)
				)
			}

			val purseTypeAdapter = setupPurseTypeAdapter().also { actvNewPurseType.setAdapter(it) }
			actvNewPurseType.setOnItemClickListener { _, _, i, _ ->
				tilNewPurseCurrency.isEnabled = true
				purseTypeAdapter.getItem(i)?.let { purseTypeObject ->
					actvNewPurseCurrency.setAdapter(setupCurrencyAdapter(purseTypeObject.type))
				}
			}

			buttonNewPurseChooseColor.setOnClickListener(::setOnChoosePurseButtonColorClickListener)

			/* TODO Credit card
			tilNewPurseCreditPercent.editText?.doOnTextChanged { text, _, _, _ ->
				text?.let { onCreditPersentTextChanged(it.toString()) }
			}
			*/
		}
	}

	private data class PurseTypeObject(val type: PurseCategoryType, val id: Int, val name: String) {
		override fun toString() = name
	}

	private fun setupPurseTypeAdapter(): ArrayAdapter<PurseTypeObject> {

		val purseTypeObjectValues = PurseCategoryType.values().map {

			val strId = when (it) {
				PurseCategoryType.CASH -> R.string.purse_type_cash
				PurseCategoryType.CREDIT_CARD -> R.string.purse_type_credit_card
				PurseCategoryType.DEBIT_CARD -> R.string.purse_type_debit_card
				PurseCategoryType.CRYPTO_CURRENCY -> R.string.purse_type_crypto_currency
				PurseCategoryType.PRECIOUS_METALS -> R.string.purse_type_precious_metal
				PurseCategoryType.SECURITIES -> R.string.purse_type_securities
			}

			PurseTypeObject(type = it, id = strId, name = getString(strId))
		}

		return ArrayAdapter(requireContext(), R.layout.item_dropdown_item, purseTypeObjectValues)
	}

	private fun setupCurrencyAdapter(purseType: PurseCategoryType): ArrayAdapter<String> =
		when (purseType) {

			//TODO API
			PurseCategoryType.PRECIOUS_METALS, PurseCategoryType.SECURITIES -> {
				setCreditPurseVisibility(View.GONE)
				ArrayAdapter(requireContext(), R.layout.item_dropdown_item, listOf(""))
			}

			PurseCategoryType.CRYPTO_CURRENCY -> {
				setCreditPurseVisibility(View.GONE)
				val adapter = ArrayAdapter<String>(requireContext(), R.layout.item_dropdown_item)

				viewModel.cryptoCoinLiveData.observe(viewLifecycleOwner) { state ->
					when (state) {
						is BaseViewModel.NetworkRequestState.SendingState -> showLoading()
						is BaseViewModel.NetworkRequestState.OnSuccessState -> {
							adapter.addAll(state.data
								.sortedBy { it.symbol }
								.map { "${it.symbol} - ${it.name}" }
							)
							hideLoading()
						}
						is BaseViewModel.NetworkRequestState.ErrorState -> {
							hideLoading()
							view?.let {
								showTextSnackBar(
									it,
									state.t.localizedMessage
										?: getString(R.string.something_went_wrong)
								)
							}
						}
						else -> hideLoading()
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
				ArrayAdapter(requireContext(), R.layout.item_dropdown_item,
					Currency.getAvailableCurrencies()
						.sortedBy { currency -> currency.currencyCode }
						.map { currency ->
							"${currency.currencyCode} - " +
									"${currency.displayName} (${currency.symbol})"
						}
				)
			}
		}

	private fun setOnChoosePurseButtonColorClickListener(view: View) {
		ColorPicker(
			requireActivity(),
			Color.red(currentColor),
			Color.green(currentColor),
			Color.blue(currentColor)
		).apply {
			setCallback { color ->
				currentColor = color
				view.setBackgroundColor(color)
			}
			enableAutoClose()
			show()
		}
	}

	private fun onCreditPercentTextChanged(text: String) {
		val showTextInputLayoutError: TextInputLayout.() -> Unit = {
			isErrorEnabled = true
			error = getString(R.string.new_purse_invalid_value_error)
		}

		with(bindingUtil.tilNewPurseCreditLimit) {
			try {
				val percent = text.toDouble()
				if (percent < 0 || percent > 100)
					showTextInputLayoutError()
				else
					isErrorEnabled = false
			} catch (t: NumberFormatException) {
				showTextInputLayoutError()
			}
		}
	}

	private fun onSaveButtonClickListener(view: View) {
		val account = Account(name = )
	}

	private fun setCreditPurseVisibility(visibility: Int) {
		bindingUtil.hsvCreditPurseElements.visibility = visibility
	}
}
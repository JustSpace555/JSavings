package ru.jsavings.presentation.ui.fragments.purses.newpurse

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputLayout
import org.koin.android.viewmodel.ext.android.viewModel
import ru.jsavings.R
import ru.jsavings.data.model.database.purse.PurseCategoryType
import ru.jsavings.databinding.PurseFragmentNewPurseBinding
import ru.jsavings.presentation.ui.fragments.common.BaseFragment
import java.util.*

class NewPurseFragment : BaseFragment() {

	override val viewModel by viewModel<NewPurseViewModel>()

	override lateinit var bindingUtil: PurseFragmentNewPurseBinding

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

		with(bindingUtil) {

			setCreditPurseVisibility(View.GONE)
			tilNewPurseCurrency.isEnabled = false

			root.setOnTouchListener { v, _ ->
				(requireContext()
					.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
				).hideSoftInputFromWindow(v.windowToken, 0)
				requireActivity().window.currentFocus?.clearFocus()
				true
			}

			viewModel.requestCryptoCoins { t ->
				showTextSnackBar(
					view,
					t.localizedMessage ?: getString(R.string.something_went_wrong)
				)
			}

			with (actvNewPurseType) {
				data class PurseTypeObject(
					val type: PurseCategoryType,
					val id: Int,
					val name: String
					) {
					override fun toString() = name
				}

				val purseTypeAdapter = ArrayAdapter(
					requireContext(),
					R.layout.item_dropdown_item,
					PurseCategoryType.values().map {

						val strId = when (it) {
							PurseCategoryType.CASH -> R.string.purse_type_cash
							PurseCategoryType.CREDIT_CARD -> R.string.purse_type_credit_card
							PurseCategoryType.DEBIT_CARD -> R.string.purse_type_debit_card
							PurseCategoryType.CRYPTO_CURRENCY ->
								R.string.purse_type_crypto_currency
							PurseCategoryType.PRECIOUS_METALS ->
								R.string.purse_type_precious_metal
							PurseCategoryType.SECURITIES -> R.string.purse_type_securities
						}

						PurseTypeObject(
							type = it,
							id = strId,
							name = getString(strId)
						)
					}
				)
				setAdapter(purseTypeAdapter)
				setOnItemClickListener { _, _, i, _ ->
					tilNewPurseCurrency.isEnabled = true
					purseTypeAdapter.getItem(i)?.let { purseTypeObject ->

						val standardCurrencyAdapter = ArrayAdapter(
							requireContext(),
							R.layout.item_dropdown_item,
							Currency.getAvailableCurrencies()
								.sortedBy { currency -> currency.currencyCode }
								.map { currency ->
									"${currency.currencyCode} - " +
									"${currency.displayName} (${currency.symbol})"
								}
						)

						val finalCurrencyAdapter = when(purseTypeObject.type) {

							//TODO API
							PurseCategoryType.PRECIOUS_METALS, PurseCategoryType.SECURITIES -> {
								setCreditPurseVisibility(View.GONE)
								ArrayAdapter(
									requireContext(),
									R.layout.item_dropdown_item,
									listOf("")
								)
							}

							PurseCategoryType.CRYPTO_CURRENCY -> {

								setCreditPurseVisibility(View.GONE)
								val adapter = ArrayAdapter<String>(
									requireContext(),
									R.layout.item_dropdown_item
								)

								viewModel.cryptoCoinLiveData.observe(viewLifecycleOwner) { state ->
									if (
										state is NewPurseViewModel.CryptoApiRequestState.OnNextState
									) {
										adapter.addAll(
											state.coins
												.sortedBy { it.symbol }
												.map { "${it.symbol} - ${it.name}" }
										)
										cpiLoading.visibility = View.GONE
									} else cpiLoading.visibility = View.VISIBLE
								}
								adapter
							}

							else -> {
								setCreditPurseVisibility(
									if (purseTypeObject.type == PurseCategoryType.CREDIT_CARD)
										View.VISIBLE
									else
										View.GONE
								)
								standardCurrencyAdapter
							}
						}

						actvNewPurseCurrency.setAdapter(finalCurrencyAdapter)
					}
				}
			}

			tilNewPurseCreditLimit.editText?.doOnTextChanged { text, _, _, _ ->

				val showTextInputLayoutError: TextInputLayout.() -> Unit = {
					isErrorEnabled = true
					error = getString(R.string.new_purse_invalid_value_error)
				}

				text?.toString()?.let {
					try {
						val percent = it.toDouble()
						if (percent < 0 || percent > 100)
							tilNewPurseCreditLimit.showTextInputLayoutError()
						tilNewPurseCreditLimit.isErrorEnabled = false

					} catch (t: NumberFormatException) {
						tilNewPurseCreditLimit.showTextInputLayoutError()
					}
				}
			}

		}
	}

	private fun setCreditPurseVisibility(visibility: Int) =
		with (bindingUtil) {
			tilNewPurseCreditLimit.visibility = visibility
			clCreditPurse.visibility = visibility
		}
}
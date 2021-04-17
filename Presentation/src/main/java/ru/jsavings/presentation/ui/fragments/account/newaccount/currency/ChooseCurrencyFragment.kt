package ru.jsavings.presentation.ui.fragments.account.newaccount.currency

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.koin.android.viewmodel.ext.android.viewModel
import ru.jsavings.R
import ru.jsavings.data.repository.sharedpreferences.NewAccountSharedPreferences
import ru.jsavings.databinding.NewAccountFragmentChooseCurrencyBinding
import ru.jsavings.presentation.ui.fragments.common.BaseFragment
import java.util.*

class ChooseCurrencyFragment : BaseFragment() {

	override val viewModel by viewModel<ChooseCurrencyViewModel>()

	override lateinit var bindingUtil: NewAccountFragmentChooseCurrencyBinding

	private val args: ChooseCurrencyFragmentArgs by navArgs()

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		bindingUtil = NewAccountFragmentChooseCurrencyBinding.inflate(inflater, container, false)
		return bindingUtil.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		val currencyList = Currency.getAvailableCurrencies()
			.sortedBy { it.currencyCode }
			.map { "${it.currencyCode} - ${it.displayName} (${it.symbol})" }

		with(bindingUtil) {

			with (actNewAccountCurrency) {
				setAdapter(ArrayAdapter(requireContext(), R.layout.item_dropdown_item, currencyList))
				setOnItemClickListener { _, _, _, _ -> buttonNewAccountNext.isEnabled = true }

				val currency = viewModel.getFromSharedPreferences(
					NewAccountSharedPreferences.JS_NEW_ACCOUNT_CURRENCY, String::class, ""
				)

				buttonNewAccountNext.isEnabled =
				if (currency.isNotEmpty()) {
					text = Editable.Factory.getInstance().newEditable(currency)
					true
				} else false

				addTextChangedListener(object : TextWatcher {
					override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
					override fun afterTextChanged(s: Editable?) {}

					override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
						s?.let { str -> buttonNewAccountNext.isEnabled = str.toString() in currencyList }
					}
				})
			}

			with (buttonNewAccountNext) {
				setOnClickListener {
					viewModel.putItemToSharedPreferences(
						NewAccountSharedPreferences.JS_NEW_ACCOUNT_CURRENCY, actNewAccountCurrency.text.toString()
					)
					findNavController().navigate(
						ChooseCurrencyFragmentDirections
							.actionChooseCurrencyNewAccountFragmentToStartingBalanceNewAccountFragment(
								navArgs<ChooseCurrencyFragmentArgs>().value.isEducationNeeded
							)
					)
				}
			}
		}
	}

	override fun onStart() {
		super.onStart()

		if (args.isEducationNeeded) {
			bindingUtil.textNote.apply {
				visibility = View.VISIBLE
				alpha = 0f
				animate()
					.alpha(1f)
					.duration = 1000
			}
		} else {
			bindingUtil.textNote.visibility = View.GONE
		}
	}
}
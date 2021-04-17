package ru.jsavings.presentation.ui.fragments.account.newaccount.balance

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.koin.android.viewmodel.ext.android.viewModel
import ru.jsavings.data.repository.sharedpreferences.NewAccountSharedPreferences
import ru.jsavings.databinding.NewAccountFragmentStartingBalanceBinding
import ru.jsavings.presentation.ui.fragments.common.BaseFragment

class StartingBalanceFragment : BaseFragment() {

	override val viewModel by viewModel<StartingBalanceViewModel>()

	override lateinit var bindingUtil : NewAccountFragmentStartingBalanceBinding

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		bindingUtil = NewAccountFragmentStartingBalanceBinding.inflate(inflater, container, false)
		return bindingUtil.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		with(bindingUtil) {

			with(buttonNewAccountNext) {
				val balance = viewModel.getFromSharedPreferences(
					NewAccountSharedPreferences.JS_NEW_ACCOUNT_STARTING_BALANCE, String::class, ""
				)
				isEnabled = balance.isNotEmpty().also {
					if (it) tilNewAccountName.editText?.text = Editable.Factory.getInstance().newEditable(balance)
				}
				setOnClickListener(::navigateToNextFragment)
			}

			buttonNewAccountSkip.setOnClickListener(::navigateToNextFragment)

			with(tilNewAccountName) {
				editText?.let {
					it.addTextChangedListener(object : TextWatcher {
						override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
						override fun afterTextChanged(s: Editable?) {}

						override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
							s?.let { numberStr ->
								with(numberStr.isNotEmpty()) {
									buttonNewAccountNext.isEnabled = this
									setEndIconActivated(this)
								}
							}
						}

					})
				}

				val currency = viewModel.getFromSharedPreferences(
					NewAccountSharedPreferences.JS_NEW_ACCOUNT_CURRENCY, String::class, ""
				)
				if (currency.isNotEmpty())
					suffixText = currency.slice(currency.indexOf('(') + 1 until currency.lastIndex)
			}
		}
	}

	private fun navigateToNextFragment(v: View) {
		viewModel.putItemToSharedPreferences(
			NewAccountSharedPreferences.JS_NEW_ACCOUNT_STARTING_BALANCE,
			bindingUtil.tieStartingBalance.text?.toString() ?: "0.0"
		)
		findNavController().navigate(
			if (navArgs<StartingBalanceFragmentArgs>().value.isEducationNeeded)
				StartingBalanceFragmentDirections.actionStartingBalanceNewAccountFragmentToCreateFirstPurseFragment()
			else
				StartingBalanceFragmentDirections.actionStartingBalanceNewAccountFragmentToNewPurseFragment()
		)
	}
}
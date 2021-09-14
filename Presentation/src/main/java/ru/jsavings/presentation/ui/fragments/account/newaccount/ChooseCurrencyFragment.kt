package ru.jsavings.presentation.ui.fragments.account.newaccount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.koin.android.viewmodel.ext.android.viewModel
import ru.jsavings.R
import ru.jsavings.databinding.FragmentNewAccountChooseCurrencyBinding
import ru.jsavings.domain.model.network.currency.Currency
import ru.jsavings.presentation.ui.fragments.common.BaseFragment
import ru.jsavings.presentation.viewmodels.account.newaccount.ChooseCurrencyViewModel

class ChooseCurrencyFragment : BaseFragment() {

	override val viewModel by viewModel<ChooseCurrencyViewModel>()
	override lateinit var bindingUtil: FragmentNewAccountChooseCurrencyBinding
	private val args: ChooseCurrencyFragmentArgs by navArgs()

	private val currenciesList = mutableListOf<Currency>()

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View {
		bindingUtil = FragmentNewAccountChooseCurrencyBinding.inflate(inflater, container, false)
		return bindingUtil.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		if (isInternetAvailable) {
			setUpObservers()
			setUpUi()
			viewModel.requestCurrencies()
		}
		else {
			findNavController().navigate(R.id.action_global_to_no_internet_fragment)
		}
	}

	private fun setUpObservers() {
		viewModel.allCurrenciesRequestState.subscribe<List<Currency>>(
			hideLoading = true,
			onSuccess = { currenciesList.addAll(it) },
			onError = { showTextSnackBar(
				text = it.getErrorString(),
				actionText = getString(R.string.retry),
				action = { viewModel.requestCurrencies() }
			)},
			onSending = { showLoading(R.string.loading_getting_currencies) }
		)

		viewModel.saveAccountRequestState.subscribe<Long>(
			hideLoading = true,
			onSuccess = {
				hideKeyBoard()
				navigateToNextFragment(args.isEducationNeeded, it)
			},
			onError = {
				hideKeyBoard()
				showTextSnackBar(it.getErrorString())
			},
			onSending = { showLoading(R.string.loading_saving_account) }
		)
	}

	private fun setUpUi() {
		with(bindingUtil) {
			hideKeyBoardOnRootTouchClick()

			if (viewModel.newAccountCurrency.isNotEmpty() && viewModel.validateNewAccountCurrency(currenciesList))
				buttonGoNext.isEnabled = true

			bindingUtil.actvNewAccountCurrency.setAdapter(
				ArrayAdapter(requireContext(), R.layout.item_dropdown_item, currenciesList)
			)

			actvNewAccountCurrency.apply {
				if (viewModel.newAccountCurrency.isNotEmpty())
					setText(viewModel.newAccountCurrency)

				setOnItemClickListener { parent, _, i, _ ->
					viewModel.newAccountCurrency = parent.getItemAtPosition(i).toString()
					buttonGoNext.isEnabled = true
				}
				addTextChangedListener { it?.let {
					tilNewAccountCurrency.isErrorEnabled = false
					buttonGoNext.isEnabled = it.isNotEmpty() && it.isNotBlank()
					viewModel.newAccountCurrency = it.toString()
				}}
			}

			if (args.isEducationNeeded) bindingUtil.textNote.apply {
				alpha = 0f
				isVisible = true
				animate()
					.alpha(1f)
					.duration = 1000
			}

			buttonGoNext.setOnClickListener { onGoNextClick() }
		}
	}

	private fun onGoNextClick() {

		if (!viewModel.validateNewAccountCurrency(currenciesList)) {
			bindingUtil.tilNewAccountCurrency.error = getString(R.string.error_new_account_currency_is_invalid)
			bindingUtil.tilNewAccountCurrency.isErrorEnabled = true
			return
		}

		val newAccountName = args.newAccountName
		viewModel.requestSaveAccount(newAccountName)
	}

	private fun navigateToNextFragment(isEducationNeeded: Boolean, accountId: Long) =
		findNavController().navigate(
			if (isEducationNeeded)
				ChooseCurrencyFragmentDirections
					.actionChooseCurrencyNewAccountFragmentToCreateFirstWalletFragment(true, accountId)
			else
				ChooseCurrencyFragmentDirections
					.actionChooseCurrencyNewAccountFragmentToNewWalletFragment(false, accountId)
		)
}
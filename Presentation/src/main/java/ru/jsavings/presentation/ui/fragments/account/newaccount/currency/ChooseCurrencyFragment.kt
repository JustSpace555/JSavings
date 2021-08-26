package ru.jsavings.presentation.ui.fragments.account.newaccount.currency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.koin.android.viewmodel.ext.android.viewModel
import ru.jsavings.R
import ru.jsavings.databinding.NewAccountFragmentChooseCurrencyBinding
import ru.jsavings.domain.model.network.currency.Currency
import ru.jsavings.presentation.ui.fragments.common.BaseFragment
import ru.jsavings.presentation.ui.fragments.common.BaseViewModel

class ChooseCurrencyFragment : BaseFragment() {

	override val viewModel by viewModel<ChooseCurrencyViewModel>()

	override lateinit var bindingUtil: NewAccountFragmentChooseCurrencyBinding

	private val args: ChooseCurrencyFragmentArgs by navArgs()

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View {
		bindingUtil = NewAccountFragmentChooseCurrencyBinding.inflate(inflater, container, false)
		return bindingUtil.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		if (isInternetAvailable)
			viewModel.requestCurrencies()
		else
			findNavController().navigate(R.id.action_global_to_no_internet_fragment)

		with(bindingUtil) {
			hideKeyBoardOnRootTouchClick(root)

			textNote.visibility = View.GONE

			if (viewModel.newAccountCurrency.isNotEmpty() && viewModel.validateNewAccountCurrency())
				buttonNewAccountNext.isEnabled = true

			actvNewAccountCurrency.setOnItemClickListener { parent, _, i, _ ->
				viewModel.newAccountCurrency = parent.getItemAtPosition(i).toString()
				buttonNewAccountNext.isEnabled = true
			}

			actvNewAccountCurrency.addTextChangedListener {
				tilNewAccountCurrency.isErrorEnabled = false
				buttonNewAccountNext.isEnabled = !(it.isNullOrEmpty() || it.isNullOrBlank())
			}

			buttonNewAccountNext.setOnClickListener { setOnButtonNewAccountClick() }
		}
	}

	override fun onStart() {
		super.onStart()

		viewModel.allCurrenciesRequestStateLiveData.observe(viewLifecycleOwner) { state ->
			when (state) {
				is BaseViewModel.RequestState.SuccessState<*> -> {
					@Suppress("UNCHECKED_CAST")
					state.data as List<Currency>

					val stringRepresentation = state.data.map { currency -> currency.toString() }
					viewModel.listOfCurrencies.addAll(stringRepresentation)
					bindingUtil.actvNewAccountCurrency.setAdapter(
						ArrayAdapter(requireContext(), R.layout.item_dropdown_item, stringRepresentation)
					)

					hideLoading()

					if (args.isEducationNeeded) {
						bindingUtil.textNote.visibility = View.VISIBLE
						bindingUtil.textNote.apply {
							visibility = View.VISIBLE
							alpha = 0f
							animate()
								.alpha(1f)
								.duration = 1000
						}
					}
				}
				is BaseViewModel.RequestState.ErrorState<*> -> {
					hideLoading()
					showTextSnackBar(
						text = state.t.getErrorString(),
						actionText = getString(R.string.retry),
						action = { viewModel.requestCurrencies() }
					)
				}
				else -> showLoading(getString(R.string.loading_getting_currencies))
			}
		}
	}

	private fun setOnButtonNewAccountClick() {

		if (!viewModel.validateNewAccountCurrency()) {
			bindingUtil.tilNewAccountCurrency.error = getString(R.string.error_new_account_currency_is_invalid)
			bindingUtil.tilNewAccountCurrency.isErrorEnabled = true
			return
		}

		val args by navArgs<ChooseCurrencyFragmentArgs>()
		val newAccountName = args.newAccountName
		viewModel.requestSaveAccount(newAccountName)

		viewModel.saveAccountRequestLiveData.observe(viewLifecycleOwner) { state ->
			when (state) {
				is BaseViewModel.RequestState.SuccessState<*> -> {
					hideLoading()
					viewModel.onAccountCreated(state.data as Long)
					navigateToNextFragment(args.isEducationNeeded)
				}
				is BaseViewModel.RequestState.ErrorState<*> -> {
					hideLoading()
					showTextSnackBar(
						text = state.t.getErrorString(),
						actionText = getString(R.string.retry),
						action = { setOnButtonNewAccountClick() }
					)
				}
				else -> showLoading()
			}
		}
	}

	private fun navigateToNextFragment(isEducationNeeded: Boolean) =
		findNavController().navigate(
			if (isEducationNeeded)
				ChooseCurrencyFragmentDirections.actionChooseCurrencyNewAccountFragmentToCreateFirstWalletFragment(true)
			else
				ChooseCurrencyFragmentDirections.actionChooseCurrencyNewAccountFragmentToNewWalletFragment(false)
		)
}
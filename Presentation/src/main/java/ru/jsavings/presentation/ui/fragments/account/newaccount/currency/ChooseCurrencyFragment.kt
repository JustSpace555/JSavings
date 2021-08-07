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
import ru.jsavings.data.model.network.currency.Currency
import ru.jsavings.databinding.NewAccountFragmentChooseCurrencyBinding
import ru.jsavings.presentation.ui.fragments.common.BaseFragment
import ru.jsavings.presentation.ui.fragments.common.BaseViewModel

class ChooseCurrencyFragment : BaseFragment() {

	override val viewModel by viewModel<ChooseCurrencyViewModel>()

	override lateinit var bindingUtil: NewAccountFragmentChooseCurrencyBinding

	private val args: ChooseCurrencyFragmentArgs by navArgs()

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
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
			hideKeyBoardOnRootTouch(root)

			with(actvNewAccountCurrency) {

				if (viewModel.newAccountCurrency.isNotEmpty()) buttonNewAccountNext.isEnabled = true

				setOnItemClickListener { _, _, i, _ ->
					viewModel.newAccountCurrency = adapter.getItem(i).toString()
					buttonNewAccountNext.isEnabled = true
				}

				//TODO Подумать как убрать
				addTextChangedListener {
					it?.let { text -> viewModel.newAccountCurrency = text.toString() }
					if (hasFocus()) buttonNewAccountNext.isEnabled = false
				}
			}

			buttonNewAccountNext.setOnClickListener { setOnButtonNewAccountClick() }
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

		val adapter = ArrayAdapter<Currency>(requireContext(), R.layout.item_dropdown_item)

		bindingUtil.actvNewAccountCurrency.setAdapter(adapter)

		viewModel.allCurrenciesRequestStateLiveData.observe(viewLifecycleOwner) { state ->
			when (state) {
				is BaseViewModel.RequestState.SuccessState<*> ->
					@Suppress("UNCHECKED_CAST")
					adapter.addAll(state.data as List<Currency>)
				is BaseViewModel.RequestState.ErrorState<*> -> {
					showTextSnackBar(
						text = state.t.getErrorString(),
						actionText = getString(R.string.retry),
						action = { viewModel.requestCurrencies() }
					)
				}
				else -> {}
			}
		}
	}

	private fun setOnButtonNewAccountClick() {

		val newAccountMainCurrency = viewModel.newAccountCurrency
			.split(' ')
			.first()
			.uppercase()

		val args by navArgs<ChooseCurrencyFragmentArgs>()
		val action = if (args.isEducationNeeded) {
			ChooseCurrencyFragmentDirections
				.actionChooseCurrencyNewAccountFragmentToCreateFirstPurseFragment(
					isEducationNeeded = true,
					newAccountName = args.newAccountName,
					newAccountMainCurrency
				)
		} else {
			ChooseCurrencyFragmentDirections
				.actionChooseCurrencyNewAccountFragmentToNewPurseFragment(
					isEducationNeeded = false,
					newAccountName = args.newAccountName,
					newAccountMainCurrency
				)
		}

		findNavController().navigate(action)
	}
}
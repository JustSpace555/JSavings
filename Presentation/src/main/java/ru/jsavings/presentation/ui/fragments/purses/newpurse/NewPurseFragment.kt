package ru.jsavings.presentation.ui.fragments.purses.newpurse

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import org.koin.android.viewmodel.ext.android.viewModel
import ru.jsavings.R
import ru.jsavings.data.model.database.purse.PurseCategoryType
import ru.jsavings.data.model.network.crypto.CryptoCoin
import ru.jsavings.databinding.PurseFragmentNewPurseBinding
import ru.jsavings.presentation.ui.fragments.common.BaseFragment
import java.util.*

class NewPurseFragment : BaseFragment() {

	override val viewModel by viewModel<NewPurseViewModel>()

	override lateinit var bindingUtil : PurseFragmentNewPurseBinding

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		bindingUtil = PurseFragmentNewPurseBinding.inflate(inflater, container, false)
		return bindingUtil.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		viewModel.requestCryptoCoins { t ->
			showTextSnackBar(
				view,
				t.localizedMessage ?: getString(R.string.something_went_wrong)
			)
		}

		with(bindingUtil) {

			with (actvNewPurseType) {
				inputType = InputType.TYPE_NULL
				val purseTypeAdapter = ArrayAdapter(
					requireContext(), R.layout.item_dropdown_item, PurseCategoryType.values()
				)
				setAdapter(purseTypeAdapter)
				setOnItemClickListener { _, _, i, _ ->
					purseTypeAdapter.getItem(i)?.let {

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

						val currencyAdapter = when(it) {
							PurseCategoryType.CREDIT_CARD -> {
								setCreditPurseVisibility(View.VISIBLE)
								standardCurrencyAdapter
							}

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
								val adapter = ArrayAdapter<CryptoCoin>(
									requireContext(),
									R.layout.item_dropdown_item
								)

								viewModel.cryptoCoinLiveData.observe(viewLifecycleOwner) { state ->
									when(state) {
										is NewPurseViewModel.CryptoApiRequestState.OnNextState -> {
											adapter.addAll(state.coins)
											cpiLoading.visibility = View.GONE
										}
										else -> cpiLoading.visibility = View.VISIBLE
									}
								}
								adapter
							}

							else -> {
								setCreditPurseVisibility(View.GONE)
								standardCurrencyAdapter
							}
						}
					}
				}
			}


		}
	}

	private fun setCreditPurseVisibility(visibility: Int) {
		bindingUtil.tilNewPurseCreditLimit.visibility = visibility
		bindingUtil.clCreditPurse.visibility = visibility
	}
}
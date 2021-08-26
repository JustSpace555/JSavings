package ru.jsavings.presentation.ui.fragments.transactions.alltransactions

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.chip.Chip
import org.koin.android.viewmodel.ext.android.viewModel
import ru.jsavings.R
import ru.jsavings.databinding.MainFragmentTransactionsBinding
import ru.jsavings.domain.model.database.Account
import ru.jsavings.domain.model.database.wallet.Wallet
import ru.jsavings.presentation.ui.activities.MainActivity
import ru.jsavings.presentation.ui.fragments.common.BaseFragment
import ru.jsavings.presentation.ui.fragments.common.BaseViewModel
import java.util.*

class TransactionsFragment : BaseFragment() {

	override val viewModel by viewModel<TransactionsViewModel>()

	override lateinit var bindingUtil: MainFragmentTransactionsBinding

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		bindingUtil = MainFragmentTransactionsBinding.inflate(inflater, container, false)
		return bindingUtil.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		requestAndSetData()
	}

	private fun requestAndSetData() {
		observeAccountRequest()
		observeWalletsRequest()
		observeTransactionsDateRequest()
		viewModel.requestAccount()
	}

	private fun observeAccountRequest() = viewModel.requestAccountState.observe(viewLifecycleOwner) { state ->
		when (state) {
			is BaseViewModel.RequestState.SuccessState<*> -> {
				viewModel.account = state.data as Account
				(requireActivity() as? MainActivity)?.setAccountName(state.data.name)
				viewModel.requestWallets()
			}
			is BaseViewModel.RequestState.ErrorState<*> -> {
				hideLoading()
				showTextSnackBar(state.t.getErrorString())
				//TODO подумать над навигацией на экран создания нового аккаунта
			}
			is BaseViewModel.RequestState.SendingState -> showLoading(getString(R.string.loading_account))
		}
	}

	@SuppressLint("SetTextI18n")
	@Suppress("UNCHECKED_CAST")
	private fun observeWalletsRequest() = viewModel.requestWalletsState.observe(viewLifecycleOwner) { state ->
		when (state) {
			is BaseViewModel.RequestState.SuccessState<*> -> {

				state.data as List<Wallet>
				if (viewModel.wallets.isEmpty())
					viewModel.wallets.addAll(state.data)
				bindingUtil.chipGroup.removeAllViews()
				val accountBalanceChip = (
						layoutInflater.inflate(R.layout.item_wallet_chip, bindingUtil.chipGroup, false) as Chip
					).apply {
						text = getString(R.string.total_balance) +
								viewModel.account.balanceInMainCurrency +
								" " +
								Currency.getInstance(viewModel.account.mainCurrencyCode).symbol
						isCheckable = true
						isChecked = true
					}
				bindingUtil.chipGroup.addView(accountBalanceChip)

				state.data.forEach {
					val walletChip = (
							layoutInflater.inflate(R.layout.item_wallet_chip, bindingUtil.chipGroup, false) as Chip
						).apply {
							val symbol = Currency.getAvailableCurrencies()
								.find { currency -> currency.currencyCode == it.currency }?.symbol ?: it.currency

							text = "${it.name}: ${it.balance} $symbol"
							//chipBackgroundColor = it.color TODO
							isCheckable = true
						}
					bindingUtil.chipGroup.addView(walletChip)
				}
				viewModel.requestLastTransactionDate()
			}
			is BaseViewModel.RequestState.ErrorState<*> -> {
				showTextSnackBar(state.t.getErrorString())
				hideLoading()
			}
			else -> setLoadingText(getString(R.string.loading_wallets))
		}
	}

	private fun observeTransactionsDateRequest() =
		viewModel.requestLastTransactionDateState.observe(viewLifecycleOwner) { state ->
			when (state) {
				is BaseViewModel.RequestState.SuccessState<*> -> {
					state.data as Date
					if (state.data.time == 0L) {
						bindingUtil.textNothingHere.visibility = View.VISIBLE
						bindingUtil.ivNothingHere.visibility = View.VISIBLE
						hideLoading()
					} else {
						val calendar = Calendar.getInstance(locale).apply {
							time = state.data
							set(Calendar.HOUR_OF_DAY, 0)
							set(Calendar.MINUTE, 0)
							set(Calendar.SECOND, 0)
							set(Calendar.MILLISECOND, 0)
							add(Calendar.MONTH, -1)
						}
						//viewModel.requestTransactions(Pair(state.data, calendar.time))
						//TODO RecyclerView
					}
				}
				is BaseViewModel.RequestState.ErrorState<*> -> {
					hideLoading()
					showTextSnackBar(state.t.getErrorString())
				}
				else -> setLoadingText(getString(R.string.loading_transactions))
			}
		}
}
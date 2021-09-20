package ru.jsavings.presentation.ui.fragments.transactions.alltransactions

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import org.koin.android.viewmodel.ext.android.sharedViewModel
import ru.jsavings.R
import ru.jsavings.databinding.FragmentTransactionsBinding
import ru.jsavings.domain.model.database.transaction.TemporalTransactions
import ru.jsavings.domain.model.database.wallet.Wallet
import ru.jsavings.presentation.extension.getCurrencySymbol
import ru.jsavings.presentation.extension.isTextBlack
import ru.jsavings.presentation.extension.toUiView
import ru.jsavings.presentation.ui.fragments.common.BaseFragment
import ru.jsavings.presentation.ui.fragments.transactions.alltransactions.recycler.TransactionAnimator
import ru.jsavings.presentation.ui.fragments.transactions.alltransactions.recycler.adapter.TemporalTransactionsAdapter
import ru.jsavings.presentation.viewmodels.MainSharedViewModel
import ru.jsavings.presentation.viewmodels.common.BaseViewModel
import java.util.*

class TransactionsFragment : BaseFragment() {

	override val viewModel by sharedViewModel<MainSharedViewModel>()
	private val bindingUtil get() = binding as FragmentTransactionsBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		observeAccountRequest()
		observeTransactionRequest()
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		binding = FragmentTransactionsBinding.inflate(inflater, container, false)
		return bindingUtil.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		observeWalletsRequest()
		viewModel.requestAccount()
	}

	private fun observeAccountRequest() = viewModel.requestAccountState.observe(this) { state ->
		when (state) {
			is BaseViewModel.RequestState.SuccessState<*> -> viewModel.requestWallets()
			is BaseViewModel.RequestState.ErrorState<*> -> {
				hideLoading()
				//TODO баг. При первом заходе приложения видимо показывается старая ошибка о том, что никакого аккаунта
				// найдено не было. Убрал через if
				//TODO РЕШЕНИЕ ПЕРЕПИСАТЬ ЧЕРЕЗ SingleLiveEvent
				if (state.t !is ClassNotFoundException) showTextSnackBar(state.t.getErrorString())
			}
			is BaseViewModel.RequestState.SendingState -> {
				showLoading(R.string.loading_account)
			}
		}
	}

	@SuppressLint("SetTextI18n")
	private fun observeWalletsRequest() = viewModel.requestWalletsState.subscribe<List<Wallet>>(
		hideLoading = false,
		onSuccess = {
			if (it.isEmpty()) {
				hideLoading()
				findNavController().navigate(
					TransactionsFragmentDirections.actionTransactionsFragmentToCreateFirstWalletFragment(
						true,
						viewModel.currentAccount.accountId
					)
				)
			} else {
				setUpWallets(it)
				viewModel.requestTransactions()
			}
		},
		onError = {
			showTextSnackBar(
				text = it.getErrorString(),
				actionText = getString(R.string.retry),
				action = { viewModel.requestWallets() }
			)
		},
		onSending = { showLoading(R.string.loading_wallets) }
	)

	//TODO баг. При создании самой первой транзакции, она не отображается
	@Suppress("UNCHECKED_CAST")
	private fun observeTransactionRequest() = viewModel.requestTransactionsState.observe(this) { state ->
		when (state) {
			is BaseViewModel.RequestState.SuccessState<*> -> {
				hideLoading()
				setUpTransactionAdapter(state.data as List<TemporalTransactions>)
			}
			is BaseViewModel.RequestState.ErrorState<*> -> {
				hideLoading()
				showTextSnackBar(state.t.getErrorString())
			}
			is BaseViewModel.RequestState.SendingState -> {
				showLoading(R.string.loading_transactions)
			}
		}
	}

	@SuppressLint("SetTextI18n")
	private fun setUpWallets(walletsList: List<Wallet>) {
		bindingUtil.chipGroup.removeAllViews()
		val accountBalanceChip = Chip(requireContext()).apply {
			text = getString(R.string.total_balance) +
					viewModel.currentAccount.balanceInMainCurrency.toUiView() +
					" " +
					Currency.getInstance(viewModel.currentAccount.mainCurrencyCode).symbol
			isCheckable = true
			isChecked = true
		}
		bindingUtil.chipGroup.addView(accountBalanceChip)

		walletsList.forEach {
			val walletChip = Chip(requireContext()).apply {
				text = "${it.name}: ${it.balance.toUiView()} ${it.currency.getCurrencySymbol()}"
				setTextColor(
					AppCompatResources.getColorStateList(
						requireContext(),
						if (it.color.isTextBlack()) R.color.black else R.color.white
					)
				)
				chipBackgroundColor = ColorStateList.valueOf(it.color)
				isCheckable = true
			}
			bindingUtil.chipGroup.addView(walletChip)
		}
	}

	private fun setUpTransactionAdapter(transactions: List<TemporalTransactions>) {
		if (transactions.isEmpty()) {
			with(bindingUtil) {
				rwTransactions.isVisible = false
				ivNothingHere.isVisible = true
				textNothingHere.isVisible = true
			}
			return
		}

		if (bindingUtil.rwTransactions.adapter == null) {
			bindingUtil.rwTransactions.apply {
				adapter = TemporalTransactionsAdapter(locale) {
					findNavController().navigate(
						TransactionsFragmentDirections.actionTransactionsFragmentToTransactionInfoFragment(
							it,
							viewModel.currentAccount.mainCurrencyCode
						)
					)
				}.apply { submitList(transactions) }
				itemAnimator = TransactionAnimator(500)
			}
		} else {
			(bindingUtil.rwTransactions.adapter as? TemporalTransactionsAdapter)?.submitList(transactions)
		}
	}
}
package ru.jsavings.presentation.ui.fragments.transactions.alltransactions

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import com.google.android.material.chip.Chip
import org.koin.android.viewmodel.ext.android.sharedViewModel
import ru.jsavings.R
import ru.jsavings.databinding.FragmentTransactionsBinding
import ru.jsavings.domain.model.database.transaction.BaseTransactionData
import ru.jsavings.domain.model.database.wallet.Wallet
import ru.jsavings.presentation.extension.getCurrencySymbol
import ru.jsavings.presentation.extension.isTextBlack
import ru.jsavings.presentation.extension.toUiView
import ru.jsavings.presentation.ui.fragments.common.BaseFragment
import ru.jsavings.presentation.ui.fragments.transactions.alltransactions.recycler.TransactionAnimator
import ru.jsavings.presentation.ui.fragments.transactions.alltransactions.recycler.TransactionListAdapter
import ru.jsavings.presentation.ui.fragments.transactions.alltransactions.recycler.TransactionListDiffUtil
import ru.jsavings.presentation.viewmodels.MainSharedViewModel
import java.util.*

class TransactionsFragment : BaseFragment() {

	override val viewModel by sharedViewModel<MainSharedViewModel>()
	override lateinit var bindingUtil: FragmentTransactionsBinding

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		bindingUtil = FragmentTransactionsBinding.inflate(inflater, container, false)
		return bindingUtil.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		observeWalletsRequest()
		observeTransactionRequest()
		viewModel.requestWallets()
	}

	@SuppressLint("SetTextI18n")
	private fun observeWalletsRequest() = viewModel.requestWalletsState.subscribe<List<Wallet>>(
		hideLoading = false,
		onSuccess = { if (it.isEmpty()) {
			hideLoading()
			findNavController().navigate(
				TransactionsFragmentDirections.actionTransactionsFragmentToCreateFirstWalletFragment(true)
			)
		} else {
			setUpWallets(it)
			viewModel.requestTransactions()
		}},
		onError = { showTextSnackBar(
			text = it.getErrorString(),
			actionText = getString(R.string.retry),
			action = { viewModel.requestWallets() }
		)},
		onSending = { showLoading(R.string.loading_wallets) }
	)

	@SuppressLint("SetTextI18n")
	private fun setUpWallets(walletsList: List<Wallet>) {
		if (walletsList.isEmpty()) {
			findNavController().navigate(
				TransactionsFragmentDirections.actionTransactionsFragmentToCreateFirstWalletFragment(true)
			)
			return
		}
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
				setTextColor(AppCompatResources.getColorStateList(
					requireContext(),
					if (it.color.isTextBlack()) R.color.black else R.color.white
				))
				chipBackgroundColor = ColorStateList.valueOf(it.color)
				isCheckable = true
			}
			bindingUtil.chipGroup.addView(walletChip)
		}
	}

	private fun observeTransactionRequest() = viewModel.requestTransactionsState.subscribe<List<BaseTransactionData>>(
		hideLoading = true,
		onSuccess = { setUpTransactionAdapter(it) },
		onError = { showTextSnackBar(
			text = it.getErrorString(),
			actionText = getString(R.string.retry),
			action = { viewModel.requestTransactions() }
		)},
		onSending = { showLoading(R.string.loading_getting_transactions) }
	)

	private fun setUpTransactionAdapter(transactions: List<BaseTransactionData>) {
		if (transactions.isEmpty()) {
			with(bindingUtil) {
				rwTransactions.isVisible = false
				ivNothingHere.isVisible = true
				textNothingHere.isVisible = true
			}
		}

		if (bindingUtil.rwTransactions.adapter == null) {
			bindingUtil.rwTransactions.apply {
				adapter = TransactionListAdapter(transactions, locale)
				itemAnimator = TransactionAnimator(500)
			}
		} else {
			(bindingUtil.rwTransactions.adapter as? TransactionListAdapter)?.let {
				DiffUtil.calculateDiff(TransactionListDiffUtil(it.transactionDataList, transactions))
					.dispatchUpdatesTo(it)
			}
		}
	}
}
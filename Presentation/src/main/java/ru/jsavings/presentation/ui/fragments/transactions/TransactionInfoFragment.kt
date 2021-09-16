package ru.jsavings.presentation.ui.fragments.transactions

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.android.viewmodel.ext.android.viewModel
import ru.jsavings.R
import ru.jsavings.databinding.FragmentTransactionInfoBinding
import ru.jsavings.domain.model.database.category.TransactionCategoryType
import ru.jsavings.domain.model.database.transaction.Transaction
import ru.jsavings.presentation.extension.getCurrencySymbol
import ru.jsavings.presentation.extension.isNotEmptyAndNotBlank
import ru.jsavings.presentation.extension.toUiView
import ru.jsavings.presentation.ui.fragments.common.BaseFragment
import ru.jsavings.presentation.viewmodels.transactions.TransactionInfoViewModel
import java.text.SimpleDateFormat

class TransactionInfoFragment : BaseFragment() {

	override lateinit var bindingUtil: FragmentTransactionInfoBinding
	override val viewModel by viewModel<TransactionInfoViewModel>()

	private val args by navArgs<TransactionInfoFragmentArgs>()

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		bindingUtil = FragmentTransactionInfoBinding.inflate(inflater, container, false)
		return bindingUtil.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setUpObserveGetTransactionById()
		setObserveDeleteTransactionById()
		viewModel.requestTransactionById(args.transactionId)
	}

	private fun setUpObserveGetTransactionById() = viewModel.requestTransactionByIdState.subscribe<Transaction>(
		hideLoading = true,
		onSuccess = { bindTransaction(it) },
		onError = {
			showTextSnackBar(
				text = it.getErrorString(),
				actionText = getString(R.string.retry),
				action = { viewModel.requestTransactionById(args.transactionId) })
		},
		onSending = { showLoading(R.string.loading_getting_transaction) }
	)

	private fun setObserveDeleteTransactionById() = viewModel.requestDeleteTransactionState.subscribe<Unit>(
		hideLoading = true,
		onSuccess = { findNavController().popBackStack() },
		onError = { showTextSnackBar(text = it.getErrorString()) },
		onSending = { showLoading(R.string.loading_deleting_transaction) }
	)

	@SuppressLint("SetTextI18n")
	private fun bindTransaction(transaction: Transaction) {

		fun getTransactionSumString(): String {
			var output = ""
			transaction.category?.let {
				val symbol = if (it.categoryType == TransactionCategoryType.INCOME) "+" else "-"
				val wallet = if (it.categoryType == TransactionCategoryType.INCOME) {
					transaction.toWallet
				} else {
					transaction.fromWallet
				}
				output = "$symbol${transaction.sumInWalletCurrency.toUiView()} " +
						"${wallet?.currency?.getCurrencySymbol() ?: "?"} " +
						"(${transaction.sumInAccountCurrency.toUiView()} " +
						"${args.accountCurrencyCode.getCurrencySymbol()})"
			} ?: run {
				output = "${transaction.sumInWalletCurrency} " +
						"${transaction.fromWallet?.currency?.getCurrencySymbol() ?: "?"} \u2192 " +
						"${transaction.transferSum?.toUiView() ?: "?"} " +
						(transaction.toWallet?.currency?.getCurrencySymbol() ?: "?")
			}
			return output
		}

		fun getTransactionTypeString(): String = transaction.category?.let {
			if (it.categoryType == TransactionCategoryType.INCOME) {
				getString(R.string.transaction_type_income)
			} else {
				getString(R.string.transaction_type_consumption)
			}
		} ?: getString(R.string.transaction_type_transfer)

		with(bindingUtil) {

			ivTransactionImage.setBackgroundColor(
				transaction.category?.color ?: ContextCompat.getColor(requireContext(), R.color.grey)
			)
			textTransactionName.text = if (transaction.description.isNotEmptyAndNotBlank()) {
				transaction.description
			} else {
				getTransactionTypeString()
			}

			val dateSDF = SimpleDateFormat("yyyy LLLL dd", locale)
			val timeSDF = SimpleDateFormat("HH : mm : ss", locale)
			textTransactionDateData.text =
				dateSDF.format(transaction.dateDay) + " - " +
				timeSDF.format(transaction.dateTime)

			textTransactionCategoryData.text =
				transaction.category?.name ?: getString(R.string.transaction_type_transfer)
			textTransactionFromWalletData.text = transaction.fromWallet?.name ?: "?"
			textTransactionToWalletData.text = transaction.toWallet?.name ?: "?"
			textTransactionSumData.text = getTransactionSumString()
			textTransactionTypeData.text = getTransactionTypeString()

			transaction.category?.let {
				if (it.categoryType == TransactionCategoryType.INCOME) {
					textTransactionFromWallet.isVisible = false
					textTransactionFromWalletData.isVisible = false
				} else {
					textTransactionToWallet.isVisible = false
					textTransactionToWalletData.isVisible = false
				}
			} ?: run {
				textTransactionCategory.isVisible = false
				textTransactionCategoryData.isVisible = false
			}

			buttonEditTransaction.setOnClickListener {
				findNavController().navigate(TransactionInfoFragmentDirections
					.actionTransactionInfoFragmentToNewTransactionFragment(
						transaction.accountId, transaction.transactionId
					)
				)
			}

			buttonDeleteTransaction.setOnClickListener {
				MaterialAlertDialogBuilder(requireContext())
					.setMessage(R.string.delete_transaction_question)
					.setNegativeButton(R.string.no) { _, _ -> }
					.setPositiveButton(R.string.yes) { _, _ -> viewModel.requestDeleteTransactionById(args.transactionId) }
					.show()
			}
		}
	}
}
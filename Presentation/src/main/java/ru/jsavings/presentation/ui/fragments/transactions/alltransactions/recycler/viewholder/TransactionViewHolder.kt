package ru.jsavings.presentation.ui.fragments.transactions.alltransactions.recycler.viewholder

import android.annotation.SuppressLint
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ru.jsavings.R
import ru.jsavings.databinding.ItemTransactionBinding
import ru.jsavings.domain.model.database.category.TransactionCategoryType
import ru.jsavings.domain.model.database.transaction.Transaction
import ru.jsavings.domain.model.database.wallet.Wallet
import ru.jsavings.presentation.extension.getCurrencySymbol
import ru.jsavings.presentation.extension.toUiView
import java.text.SimpleDateFormat

class TransactionViewHolder(
	private val bindingUtil: ItemTransactionBinding
) : RecyclerView.ViewHolder(bindingUtil.root) {

	private fun Wallet?.getCurrencySymbol() = if (this == null) "?" else currency.getCurrencySymbol()

	private fun makeTransactionText(transaction: Transaction): String = when (transaction.category?.categoryType) {
		TransactionCategoryType.INCOME ->
			"+ ${transaction.sumInWalletCurrency.toUiView()} ${transaction.toWallet.getCurrencySymbol()}"
		TransactionCategoryType.CONSUMPTION ->
			"- ${transaction.sumInWalletCurrency.toUiView()} ${transaction.fromWallet.getCurrencySymbol()}"
		else -> if (transaction.fromWallet?.currency == transaction.toWallet?.currency) {
			"${transaction.sumInWalletCurrency} ${transaction.fromWallet?.currency?.getCurrencySymbol() ?: "?"}"
		} else {
			"${transaction.sumInWalletCurrency.toUiView()} ${transaction.fromWallet.getCurrencySymbol()} \u2192 " +
			"${transaction.transferSum?.toUiView() ?: "?"} ${transaction.toWallet.getCurrencySymbol()}"
		}
	}

	@SuppressLint("SetTextI18n", "SimpleDateFormat")
	fun setUpViewHolder(transaction: Transaction, onTransactionClick: (Long) -> Unit) {
		val context = itemView.context
		with(bindingUtil) {
			root.setOnClickListener { onTransactionClick(transaction.transactionId) }

			ivTransactionIcon.setBackgroundColor(transaction.category?.color ?: R.color.grey) //TODO иконки

			textTransactionDescription.text = if (transaction.description.isNotEmpty()) {
				transaction.description
			} else {
				transaction.category?.name ?: context.getString(R.string.transaction_type_transfer)
			}

			//TODO Подумать как избежать "?"
			when {
				transaction.category?.categoryType == TransactionCategoryType.INCOME -> {
					textTransactionWalletName.text = transaction.toWallet?.name ?: "?"
					textTransactionSum.apply {
						text = makeTransactionText(transaction)
						setTextColor(ContextCompat.getColor(context, R.color.primary))
					}
				}
				transaction.category?.categoryType == TransactionCategoryType.CONSUMPTION -> {
					textTransactionWalletName.text = transaction.fromWallet?.name ?: "?"
					textTransactionSum.apply {
						text = makeTransactionText(transaction)
						setTextColor(ContextCompat.getColor(context, R.color.red))
					}
				}
				transaction.category == null -> {
					textTransactionWalletName.text =
						"${transaction.fromWallet?.name ?: "?"} \u2192 ${transaction.toWallet?.name ?: "?"}"
					textTransactionSum.apply {
						text = makeTransactionText(transaction)
						setTextColor(ContextCompat.getColor(context, R.color.grey))
					}
				}
				else -> {}
			}

			textTransactionTimeHoursMinutes.text = SimpleDateFormat("HH : mm").format(transaction.dateTime)
			textTransactionTimeSeconds.text = SimpleDateFormat("ss").format(transaction.dateTime)
		}
	}
}
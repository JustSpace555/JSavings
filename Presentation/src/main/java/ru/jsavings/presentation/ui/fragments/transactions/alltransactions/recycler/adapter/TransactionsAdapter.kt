package ru.jsavings.presentation.ui.fragments.transactions.alltransactions.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.jsavings.databinding.ItemTransactionBinding
import ru.jsavings.domain.model.database.transaction.Transaction
import ru.jsavings.presentation.ui.fragments.transactions.alltransactions.recycler.viewholder.TransactionViewHolder

class TransactionsAdapter(
	private val onTransactionClick: (Long) -> Unit
) : ListAdapter<Transaction, TransactionViewHolder>(Companion) {

	companion object : DiffUtil.ItemCallback<Transaction>() {

		override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean =
			oldItem.transactionId == newItem.transactionId

		override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean =
			oldItem.category?.color == newItem.category?.color &&
			oldItem.description == newItem.description &&
			oldItem.fromWallet?.walletId == newItem.fromWallet?.walletId &&
			oldItem.toWallet?.walletId == newItem.toWallet?.walletId &&
			oldItem.sumInWalletCurrency == newItem.sumInWalletCurrency &&
			oldItem.transferSum == newItem.transferSum &&
			oldItem.dateTime.time == newItem.dateTime.time &&
			oldItem.dateDay.time == newItem.dateDay.time
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder = TransactionViewHolder(
		ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
	)

	override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) =
		holder.setUpViewHolder(getItem(position), onTransactionClick)
}
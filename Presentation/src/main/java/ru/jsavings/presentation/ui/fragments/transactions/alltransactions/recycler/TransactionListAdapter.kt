package ru.jsavings.presentation.ui.fragments.transactions.alltransactions.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.jsavings.databinding.ItemTotalTransactionsPerDayBinding
import ru.jsavings.databinding.ItemTransactionBinding
import ru.jsavings.domain.model.database.transaction.BaseTransactionData
import ru.jsavings.domain.model.database.transaction.TemporalTransactions
import ru.jsavings.domain.model.database.transaction.Transaction
import ru.jsavings.presentation.ui.fragments.transactions.alltransactions.recycler.viewholder.TransactionViewHolder
import ru.jsavings.presentation.ui.fragments.transactions.alltransactions.recycler.viewholder.TransactionsPerDayViewHolder
import java.util.*

class TransactionListAdapter(
	val transactionDataList: List<BaseTransactionData>,
	private val locale: Locale,
	private val onTransactionClick: (Long) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

	companion object {
		private const val TRANSACTION_VIEW_HOLDER = 1
		private const val DAY_VIEW_HOLDER = 2
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
		if (viewType == TRANSACTION_VIEW_HOLDER)
			TransactionViewHolder(ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false))
		else
			TransactionsPerDayViewHolder(
				ItemTotalTransactionsPerDayBinding.inflate(LayoutInflater.from(parent.context), parent, false),
				locale
			)

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		if (holder is TransactionsPerDayViewHolder) {
			holder.setUpViewHolder(transactionDataList[position] as TemporalTransactions)
		} else if (holder is TransactionViewHolder) {
			holder.setUpViewHolder(transactionDataList[position] as Transaction, onTransactionClick)
		}
	}

	override fun getItemViewType(position: Int): Int = if (transactionDataList[position] is Transaction)
		TRANSACTION_VIEW_HOLDER
	else
		DAY_VIEW_HOLDER

	override fun getItemCount(): Int = transactionDataList.size
}
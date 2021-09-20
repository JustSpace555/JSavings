package ru.jsavings.presentation.ui.fragments.transactions.alltransactions.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.jsavings.databinding.ItemTotalTransactionsPerDayBinding
import ru.jsavings.domain.model.database.transaction.TemporalTransactions
import ru.jsavings.presentation.ui.fragments.transactions.alltransactions.recycler.viewholder.TransactionsPerDayViewHolder
import java.util.*

class TemporalTransactionsAdapter(
	private val locale: Locale,
	private val onTransactionClick: (Long) -> Unit
) : ListAdapter<TemporalTransactions, TransactionsPerDayViewHolder>(Companion) {

	companion object : DiffUtil.ItemCallback<TemporalTransactions>() {

		override fun areItemsTheSame(oldItem: TemporalTransactions, newItem: TemporalTransactions): Boolean =
			oldItem.dayOfTransactions.time == newItem.dayOfTransactions.time

		override fun areContentsTheSame(oldItem: TemporalTransactions, newItem: TemporalTransactions): Boolean = when {
			oldItem.transactions.size != newItem.transactions.size -> false
			oldItem.totalIncome != newItem.totalIncome || oldItem.totalConsumption != newItem.totalConsumption -> false
			else -> oldItem.transactions.asSequence().zip(newItem.transactions.asSequence()).all {
				TransactionsAdapter.areItemsTheSame(it.first, it.second)
			}
		}
	}

	private val viewPool = RecyclerView.RecycledViewPool()

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionsPerDayViewHolder =
		TransactionsPerDayViewHolder(
			ItemTotalTransactionsPerDayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		)

	override fun onBindViewHolder(holder: TransactionsPerDayViewHolder, position: Int) {
		holder.setUpViewHolder(getItem(position), locale, onTransactionClick)
		holder.bindingUtil.rwTransaction.setRecycledViewPool(viewPool)
	}
}
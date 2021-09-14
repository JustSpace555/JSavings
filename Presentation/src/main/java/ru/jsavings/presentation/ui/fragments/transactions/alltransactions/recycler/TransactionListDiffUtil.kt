package ru.jsavings.presentation.ui.fragments.transactions.alltransactions.recycler

import androidx.recyclerview.widget.DiffUtil
import ru.jsavings.domain.model.database.transaction.BaseTransactionData
import ru.jsavings.domain.model.database.transaction.TemporalTransactions
import ru.jsavings.domain.model.database.transaction.Transaction
import java.util.SortedMap

class TransactionListDiffUtil(
	private val oldList: List<BaseTransactionData>,
	private val newList: List<BaseTransactionData>
) : DiffUtil.Callback() {

	override fun getOldListSize(): Int = oldList.size
	override fun getNewListSize(): Int = newList.size

	override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
		if (oldList[oldItemPosition]::class != newList[newItemPosition]::class) return false

		return if (oldList[oldItemPosition] is TemporalTransactions) {
			(oldList[oldItemPosition] as TemporalTransactions).dayOfTransactions ==
			(newList[newItemPosition] as TemporalTransactions).dayOfTransactions
		} else {
			(oldList[oldItemPosition] as Transaction).transactionId ==
			(newList[newItemPosition] as Transaction).transactionId
		}
	}

	override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
		oldList[oldItemPosition] == newList[newItemPosition]

//	TODO подумать
//	override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
//		return super.getChangePayload(oldItemPosition, newItemPosition)
//	}
}
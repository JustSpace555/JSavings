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
			(oldList[oldItemPosition] as TemporalTransactions).dayOfTransactions.time ==
			(newList[newItemPosition] as TemporalTransactions).dayOfTransactions.time
		} else {
			(oldList[oldItemPosition] as Transaction).transactionId ==
			(newList[newItemPosition] as Transaction).transactionId
		}
	}

	//TODO баг. Почему то при обновлении транзакции и возвращении на экран списка транзакций не обновляются данные
	// транзакции. Но при следующей отрисовке обновляются. Судя по логам, в адаптер поститсья список без изменений
	override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
		val oldItem = oldList[oldItemPosition]
		val newItem = newList[newItemPosition]

		return if (oldItem is TemporalTransactions) {
			newItem as TemporalTransactions
			oldItem.totalConsumption == newItem.totalConsumption && oldItem.totalIncome == newItem.totalIncome
		} else {
			oldItem as Transaction
			newItem as Transaction
			oldItem.category?.categoryId == newItem.category?.categoryId &&
			oldItem.description == newItem.description &&
			oldItem.toWallet?.walletId == newItem.toWallet?.walletId &&
			oldItem.fromWallet?.walletId == newItem.fromWallet?.walletId &&
			oldItem.sumInWalletCurrency == newItem.sumInWalletCurrency &&
			oldItem.dateDay.time == newItem.dateDay.time &&
			oldItem.dateTime.time == newItem.dateTime.time
		}
	}

//	TODO подумать
//	override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
//		return super.getChangePayload(oldItemPosition, newItemPosition)
//	}
}
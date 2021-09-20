package ru.jsavings.presentation.ui.fragments.transactions.alltransactions.recycler.viewholder

import android.annotation.SuppressLint
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import ru.jsavings.databinding.ItemTotalTransactionsPerDayBinding
import ru.jsavings.domain.model.database.transaction.TemporalTransactions
import ru.jsavings.presentation.extension.toUiView
import ru.jsavings.presentation.ui.fragments.transactions.alltransactions.recycler.adapter.TransactionsAdapter
import java.text.SimpleDateFormat
import java.util.*

/**
 * ViewHolder for [TemporalTransactions]
 * @param bindingUtil [ItemTotalTransactionsPerDayBinding]
 * @param locale [Locale] for translating day of week to current language
 *
 * @author JustSpace
 */
class TransactionsPerDayViewHolder(
	val bindingUtil: ItemTotalTransactionsPerDayBinding,
) : RecyclerView.ViewHolder(bindingUtil.root) {

	/**
	 * Set up view holder with information
	 * @param temporalTransactions [TemporalTransactions] to display
	 *
	 * @author JustSpace
	 */
	@SuppressLint("SetTextI18n")
	fun setUpViewHolder(temporalTransactions: TemporalTransactions, locale: Locale, onTransactionClick: (Long) -> Unit) {
		val calendar = Calendar.getInstance(locale)
		val weekDaySDF = SimpleDateFormat("EEEE", locale)
		val monthAndYearSDF = SimpleDateFormat("LLLL yyyy", locale)
		with(bindingUtil) {
			textMonthDay.text = calendar
				.apply { time = temporalTransactions.dayOfTransactions }
				.get(Calendar.DAY_OF_MONTH)
				.toString()
			textWeekDay.text = weekDaySDF.format(temporalTransactions.dayOfTransactions)
				.replaceFirstChar { if (it.isLowerCase()) it.titlecase(locale) else it.toString() }
			textMonthAndYear.text = monthAndYearSDF.format(temporalTransactions.dayOfTransactions)
				.replaceFirstChar { if (it.isLowerCase()) it.titlecase(locale) else it.toString() }

			if (temporalTransactions.totalIncome == 0.0) {
				incomeLl.isVisible = false
			} else {
				textIncome.text = temporalTransactions.totalIncome.toUiView()
			}

			if (temporalTransactions.totalConsumption == 0.0) {
				consumptionLl.isVisible = false
			} else {
				textConsumption.text = temporalTransactions.totalConsumption.toUiView()
			}

			textProfit.text = temporalTransactions.profit.toUiView()

			val adapter = TransactionsAdapter(onTransactionClick).apply {
				submitList(temporalTransactions.transactions)
			}
			rwTransaction.adapter = adapter
		}
	}
}
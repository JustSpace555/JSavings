package ru.jsavings.presentation.ui.fragments.transactions.alltransactions.recycler.viewholder

import android.annotation.SuppressLint
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import ru.jsavings.databinding.ItemTotalTransactionsPerDayBinding
import ru.jsavings.domain.model.database.transaction.TemporalTransactions
import ru.jsavings.presentation.extension.toUiView
import java.util.*

/**
 * ViewHolder for [TemporalTransactions]
 * @param bindingUtil [ItemTotalTransactionsPerDayBinding]
 * @param locale [Locale] for translating day of week to current language
 *
 * @author JustSpace
 */
class TransactionsPerDayViewHolder(
	private val bindingUtil: ItemTotalTransactionsPerDayBinding,
	private val locale: Locale
) : RecyclerView.ViewHolder(bindingUtil.root) {

	/**
	 * Set up view holder with information
	 * @param temporalTransactions [TemporalTransactions] to display
	 *
	 * @author JustSpace
	 */
	@SuppressLint("SetTextI18n")
	fun setUpViewHolder(temporalTransactions: TemporalTransactions) {
		val dayCal = Calendar.getInstance(locale).apply { time = temporalTransactions.dayOfTransactions }
		with(bindingUtil) {
			textMonthDay.text = dayCal[Calendar.DAY_OF_MONTH].toString()
			textWeekDay.text = dayCal[Calendar.DAY_OF_WEEK].toString()
			textMonthAndYear.text = "${dayCal[Calendar.MONTH]} ${dayCal[Calendar.YEAR]}"

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
		}
	}
}
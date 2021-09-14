package ru.jsavings.domain.model.database.transaction

import java.util.Date


/**
 * Represents transactions per day data. Is needed for ui.
 * @param dayOfTransactions Day of transactions
 * @param totalIncome Income of all transactions which were made in [dayOfTransactions] in account currency
 * @param totalConsumption Consumption of all transactions which were made in [dayOfTransactions] in account currency
 *
 * @author JustSpace
 */
data class TemporalTransactions(
	val dayOfTransactions: Date,
	val totalIncome: Double,
	val totalConsumption: Double
) : BaseTransactionData {

	/**
	 * Total profit of day [dayOfTransactions]
	 *
	 * @author JustSpace
	 */
	val profit = totalIncome - totalConsumption
}
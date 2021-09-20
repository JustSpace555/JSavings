package ru.jsavings.domain.model.database.transaction

import ru.jsavings.domain.model.database.category.TransactionCategoryType
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
	val transactions: List<Transaction>,
//	val totalIncome: Double,
//	val totalConsumption: Double
) : BaseTransactionData {

	val totalIncome = transactions.sumOf {
		if (it.category != null && it.category.categoryType == TransactionCategoryType.INCOME)
			it.sumInAccountCurrency
		else 0.0
	}

	val totalConsumption = transactions.sumOf {
		if (it.category != null && it.category.categoryType == TransactionCategoryType.CONSUMPTION)
			it.sumInAccountCurrency
		else
			0.0
	}

	/**
	 * Total profit of day [dayOfTransactions]
	 *
	 * @author JustSpace
	 */
	val profit = totalIncome - totalConsumption
}
package ru.jsavings.domain.model.database.transaction

import ru.jsavings.data.entity.database.TransactionCategoryEntity
import ru.jsavings.domain.model.common.BaseModel
import ru.jsavings.domain.model.database.category.TransactionCategory
import ru.jsavings.domain.model.database.wallet.Wallet
import ru.jsavings.domain.model.database.category.TransactionCategoryType
import java.text.SimpleDateFormat
import java.util.*

/**
 * Model representation of database [ru.jsavings.data.entity.database.TransactionEntity]
 * Other info such as ToWallet, FromWallet and Category must be in [TransactionGroup].
 * @param accountId Id of account to which transaction belongs
 * @param transactionId Id of transaction category to which transaction belongs
 * @param fromWallet [Wallet]. Must be not null only if [TransactionCategoryType.CONSUMPTION] or
 * [TransactionCategoryType.TRANSFER]. Represents wallet from which money were writing off
 * @param toWallet [Wallet]. Must be not null only if [TransactionCategoryType.INCOME] or
 * [TransactionCategoryType.TRANSFER]. Represents wallet to which money were added.
 * @param category [TransactionCategory]. Must be null only if [TransactionCategoryType.TRANSFER]
 * @param sumInWalletCurrency Amount of money in current transaction
 * @param transferSum Amount of money in wallet currency which id is
 * [ru.jsavings.data.entity.database.TransactionEntity.toWalletFkId], which were transferred into this wallet.
 * Must be not null only if [TransactionCategoryEntity.type] is TRANSFER
 * @param sumInAccountCurrency Sum of transaction in account's currency to which this transaction belongs
 * @param dateDay Day when transaction was made
 * @param dateTime Time when transaction was made
 * @param description Description of transaction
 * @param describePicturePath Path to transaction image
 *
 * @author JustSpace
 */
data class Transaction(
	val transactionId: Long = 0,
	val accountId: Long,
	val fromWallet: Wallet?,
	val toWallet: Wallet?,
	val category: TransactionCategory?,
	val sumInWalletCurrency: Double,
	val transferSum: Double?,
	val sumInAccountCurrency: Double,
	val dateDay: Date,
	val dateTime: Date,
	val description: String,
	val describePicturePath: String
) : BaseModel, BaseTransactionData {
	companion object {
		/**
		 * Pattern for [java.text.SimpleDateFormat] transaction day
		 *
		 * @author JustSpace
		 */
		const val DATE_PATTERN = "yyyy MMMM dd"

		/**
		 * Pattern for [java.text.SimpleDateFormat] transaction time
		 *
		 * @author JustSpace
		 */
		const val TIME_PATTERN = "HH:mm:ss"
	}
}
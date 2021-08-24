package ru.jsavings.domain.model.database.transaction

import ru.jsavings.domain.model.common.BaseModel
import ru.jsavings.domain.model.database.transaction.category.TransactionCategory
import ru.jsavings.domain.model.database.wallet.Wallet
import java.util.Date

/**
 * Model representation of database [ru.jsavings.data.entity.database.TransactionEntity]
 * @param transactionCategory Category of transaction to which it belongs
 * @param wallet Wallet to which transaction belongs
 * @param accountId Id of account to which transaction belongs
 * @param transactionId Id of transaction category to which transaction belongs
 * @param totalSum Amount of money in current transaction
 * @param date Date when transaction was made
 * @param description Description of transaction
 * @param describePicturePath Path to transaction image
 *
 * @author JustSpace
 */
data class Transaction(
	val transactionId: Long = 0,
	val wallet: Wallet,
	val accountId: Long,
	val transactionCategory: TransactionCategory,
	val totalSum: Double,
	val date: Date,
	val description: String = "",
	val describePicturePath: String? = null
) : BaseModel
package ru.jsavings.domain.mappers.database

import ru.jsavings.data.entity.database.TransactionCategoryEntity
import ru.jsavings.data.entity.database.TransactionEntity
import ru.jsavings.data.entity.database.WalletEntity
import ru.jsavings.domain.model.database.transaction.Transaction
import java.util.*

/**
 * Mapper for [TransactionEntity] and [Transaction]
 * @param walletMapper Mapper for wallets
 * @param transactionCategoryMapper Mapper for transaction category
 *
 * @author JustSpace
 */
class TransactionMapper(
	private val transactionCategoryMapper: TransactionCategoryMapper,
	private val walletMapper: WalletMapper
) {

	fun mapEntityToModel(
		transactionEntity: TransactionEntity,
		walletEntity: WalletEntity,
		transactionCategoryEntity: TransactionCategoryEntity
	): Transaction = Transaction(
		accountId = transactionEntity.accountFkId,
		date = Date(transactionEntity.date),
		totalSum = transactionEntity.sum,
		transactionCategory = transactionCategoryMapper.mapEntityToModel(transactionCategoryEntity),
		wallet = walletMapper.mapEntityToModel(walletEntity),
		transactionId = transactionEntity.transactionId,
		description = transactionEntity.description,
		describePicturePath = transactionEntity.describePicturePath
	)

	fun mapModelToEntity(model: Transaction): TransactionEntity = TransactionEntity(
		transactionId = model.transactionId,
		accountFkId = model.accountId,
		date = model.date.time,
		sum = model.totalSum,
		categoryFkId = model.transactionCategory.categoryId,
		walletFkId = model.wallet.walletId,
		describePicturePath = model.describePicturePath,
		description = model.description
	)
}
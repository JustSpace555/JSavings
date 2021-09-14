package ru.jsavings.domain.mappers.database

import ru.jsavings.data.entity.database.TransactionEntity
import ru.jsavings.data.entity.database.TransactionGroupEntity
import ru.jsavings.domain.mappers.common.BaseMapper
import ru.jsavings.domain.model.database.transaction.Transaction
import java.text.SimpleDateFormat
import java.util.Date

/**
 * Mapper for [TransactionEntity] and [Transaction]
 * @param transactionCategoryMapper [TransactionCategoryMapper]
 * @param walletMapper [WalletMapper]
 *
 * @author JustSpace
 */
class TransactionMapper(
	private val transactionCategoryMapper: TransactionCategoryMapper,
	private val walletMapper: WalletMapper
) : BaseMapper {

	/**
	 * Maps [TransactionGroupEntity] to [Transaction]
	 * @param transactionGroupEntity Entity to map
	 * @return [Transaction] result of mapping
	 * @author JustSpace
	 */
	fun mapEntityToModel(transactionGroupEntity: TransactionGroupEntity): Transaction = Transaction(
		accountId = transactionGroupEntity.transactionEntity.accountFkId,
		transferSum = transactionGroupEntity.transactionEntity.transferSum,
		transactionId = transactionGroupEntity.transactionEntity.transactionId,
		description = transactionGroupEntity.transactionEntity.description,
		describePicturePath = transactionGroupEntity.transactionEntity.describePicturePath,
		sumInAccountCurrency = transactionGroupEntity.transactionEntity.sumInAccountCurrency,
		sumInWalletCurrency = transactionGroupEntity.transactionEntity.sumInWalletCurrency,
		category = transactionGroupEntity.categoryEntity?.let { transactionCategoryMapper.mapEntityToModel(it) },
		fromWallet = transactionGroupEntity.fromWalletEntity?.let { walletMapper.mapEntityToModel(it) },
		toWallet = transactionGroupEntity.toWalletEntity?.let { walletMapper.mapEntityToModel(it) },
		dateDay = Date(transactionGroupEntity.transactionEntity.dateDay),
		dateTime = Date(transactionGroupEntity.transactionEntity.dateTime)
	)

	/**
	 * Maps [Transaction] to [TransactionEntity]
	 * @param model [Transaction] to map
	 * @return [TransactionEntity]
	 *
	 * @author JustSpace
	 */
	fun mapModelToEntity(model: Transaction): TransactionEntity = TransactionEntity(
		transactionId = model.transactionId,
		accountFkId = model.accountId,
		sumInWalletCurrency = model.sumInWalletCurrency,
		transferSum = model.transferSum,
		describePicturePath = model.describePicturePath,
		description = model.description,
		sumInAccountCurrency = model.sumInAccountCurrency,
		categoryFkId = model.category?.categoryId,
		fromWalletFkId = model.fromWallet?.walletId,
		toWalletFkId = model.toWallet?.walletId,
		dateDay = model.dateDay.time,
		dateTime = model.dateTime.time
	)
}
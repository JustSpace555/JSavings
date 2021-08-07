package ru.jsavings.data.mappers.database.transaction

import ru.jsavings.data.entity.common.BaseEntity
import ru.jsavings.data.entity.database.purse.PurseEntity
import ru.jsavings.data.entity.database.transaction.TransactionEntity
import ru.jsavings.data.mappers.common.BaseDataBaseMapper
import ru.jsavings.data.mappers.database.PurseMapper
import ru.jsavings.data.model.database.transaction.Transaction
import java.util.*

internal class TransactionMapper(
	private val purseMapper: PurseMapper
) : BaseDataBaseMapper<TransactionEntity, Transaction> {

	override fun mapEntityToModel(input: TransactionEntity, vararg additionalElements: BaseEntity): Transaction =
		Transaction(
			transactionId = input.transactionId,
			purse = purseMapper.mapEntityToModel(additionalElements.filterIsInstance<PurseEntity>().first()),
			transactionCategoryId = input.categoryFkId,
			totalSum = input.totalSum,
			date = Date(input.date),
			description = input.description,
			describePicturePath = input.describePicturePath
		)

	override fun mapModelToEntity(input: Transaction): TransactionEntity =
		TransactionEntity(
			transactionId = input.transactionId,
			purseFkId = input.purse.purseId,
			categoryFkId = input.transactionCategoryId,
			totalSum = input.totalSum,
			date = input.date.time,
			description = input.description,
			describePicturePath = input.describePicturePath
		)
}
package ru.jsavings.data.mappers.transaction

import ru.jsavings.data.entity.common.BaseEntity
import ru.jsavings.data.entity.database.purse.PurseEntity
import ru.jsavings.data.entity.database.transaction.TransactionEntity
import ru.jsavings.data.mappers.BaseMapper
import ru.jsavings.data.mappers.PurseMapper
import ru.jsavings.data.model.transaction.Transaction
import java.util.*

internal class TransactionMapper(
	private val purseMapper: PurseMapper
) : BaseMapper<TransactionEntity, Transaction> {

	// additionElements must contain TransactionCategory
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
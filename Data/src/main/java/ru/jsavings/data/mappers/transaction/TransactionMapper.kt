package ru.jsavings.data.mappers.transaction

import ru.jsavings.data.entity.BaseEntity
import ru.jsavings.data.entity.transaction.TransactionCategoryEntity
import ru.jsavings.data.entity.transaction.TransactionEntity
import ru.jsavings.data.mappers.BaseMapper
import ru.jsavings.data.model.BaseModel
import ru.jsavings.data.model.transaction.Transaction
import ru.jsavings.data.model.transaction.TransactionCategory
import java.util.*

internal class TransactionMapper(
	private val transactionCategoryMapper: TransactionCategoryMapper
) : BaseMapper<TransactionEntity, Transaction> {

	// additionElements must contain TransactionCategory
	override fun mapEntityToModel(input: TransactionEntity, vararg additionalElements: BaseEntity): Transaction =
		Transaction(
			transactionId = input.transactionId,
			purseName = input.purseFkName,
			transactionCategory = transactionCategoryMapper.mapEntityToModel(
				additionalElements.filterIsInstance<TransactionCategoryEntity>().first()
			),
			totalSum = input.totalSum,
			date = Date(input.date),
			description = input.description,
			describePicturePath = input.describePicturePath
		)

	// additionalElementsIds must contain Id of Category on first place
	override fun mapModelToEntity(input: Transaction): TransactionEntity =
		TransactionEntity(
			transactionId = 0,
			purseFkName = input.purseName,
			categoryFkName = input.transactionCategory.name,
			totalSum = input.totalSum,
			date = input.date.time,
			description = input.description,
			describePicturePath = input.describePicturePath
		)
}
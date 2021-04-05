package ru.jsavings.data.mappers.transaction

import ru.jsavings.data.entity.transaction.TransactionEntity
import ru.jsavings.data.mappers.BaseMapper
import ru.jsavings.data.model.BaseModel
import ru.jsavings.data.model.transaction.Transaction
import ru.jsavings.data.model.transaction.category.TransactionCategory
import java.util.*

internal class TransactionMapper : BaseMapper<TransactionEntity, Transaction> {

	// additionElements must contain TransactionCategory
	override fun mapEntityToModel(input: TransactionEntity, vararg additionalElements: BaseModel): Transaction =
		Transaction(
			purseId = input.purseFkId,
			categoryId = input.categoryFkId,
			transactionCategory = additionalElements.filterIsInstance<TransactionCategory>().first(),
			totalSum = input.totalSum,
			date = Date(input.date),
			description = input.description,
			describePicturePath = input.describePicturePath
		)

	// additionalElementsIds must contain Id of Category on first place
	override fun mapModelToEntity(input: Transaction, vararg additionalElementIds: Int): TransactionEntity =
		TransactionEntity(
			transactionId = 0,
			purseFkId = input.purseId,
			categoryFkId = input.categoryId,
			transactionCategoryId = additionalElementIds.first(),
			totalSum = input.totalSum,
			date = input.date.time,
			description = input.description,
			describePicturePath = input.describePicturePath
		)
}
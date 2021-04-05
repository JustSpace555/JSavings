package ru.jsavings.data.entity.binding

import androidx.room.Embedded
import androidx.room.Relation
import ru.jsavings.data.entity.transaction.TransactionCategoryEntity
import ru.jsavings.data.entity.transaction.TransactionEntity

internal data class CategoryWithTransactionsEntity (

	@Embedded
	val categoryEntity: TransactionCategoryEntity,

	@Relation(
		parentColumn = "category_id",
		entityColumn = "category_fk_id"
	)
	val transactionsEntityList: List<TransactionEntity>
)
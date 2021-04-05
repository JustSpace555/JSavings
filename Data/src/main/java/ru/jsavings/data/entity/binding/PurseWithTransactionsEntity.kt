package ru.jsavings.data.entity.binding

import androidx.room.Embedded
import androidx.room.Relation
import ru.jsavings.data.entity.PurseEntity
import ru.jsavings.data.entity.transaction.TransactionEntity

internal data class PurseWithTransactionsEntity (

	@Embedded
	val purseEntity: PurseEntity,

	@Relation (
		parentColumn = "purse_id",
		entityColumn = "purse_fk_id"
	)
	val transactionsEntityList: List<TransactionEntity>
)
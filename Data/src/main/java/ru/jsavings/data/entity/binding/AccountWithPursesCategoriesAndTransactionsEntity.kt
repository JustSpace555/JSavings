package ru.jsavings.data.entity.binding

import androidx.room.Embedded
import androidx.room.Relation
import ru.jsavings.data.entity.AccountEntity
import ru.jsavings.data.entity.PurseEntity
import ru.jsavings.data.entity.transaction.TransactionCategoryEntity

internal data class AccountWithPursesCategoriesAndTransactionsEntity (

	@Embedded
	val account: AccountEntity,

	@Relation(
		parentColumn = "account_id",
		entityColumn = "account_fk_id",
		entity = PurseEntity::class
	)
	val pursesWithTransactionsEntityList: List<PurseWithTransactionsEntity>,

	@Relation(
		parentColumn = "account_id",
		entityColumn = "account_fk_id",
		entity = TransactionCategoryEntity::class
	)
	val categoriesWithTransactionsEntityList: List<CategoryWithTransactionsEntity>
)
package ru.jsavings.data.entity.database.binding

import androidx.room.Embedded
import androidx.room.Relation
import ru.jsavings.data.entity.database.account.AccountEntity
import ru.jsavings.data.entity.database.common.BaseDbEntity
import ru.jsavings.data.entity.database.purse.PurseEntity

internal data class AccountWithPursesEntity(

	@Embedded
	val accountEntity: AccountEntity,

	@Relation(
		parentColumn = "account_id",
		entityColumn = "account_fk_id"
	)
	val purseEntities: List<PurseEntity>
) : BaseDbEntity
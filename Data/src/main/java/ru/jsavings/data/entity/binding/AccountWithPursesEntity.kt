package ru.jsavings.data.entity.binding

import androidx.room.Embedded
import androidx.room.Relation
import ru.jsavings.data.entity.AccountEntity
import ru.jsavings.data.entity.BaseEntity
import ru.jsavings.data.entity.PurseEntity

internal data class AccountWithPursesEntity(

	@Embedded
	val accountEntity: AccountEntity,

	@Relation(
		parentColumn = "account_id",
		entityColumn = "account_fk_id"
	)
	val purseEntities: List<PurseEntity>
) : BaseEntity
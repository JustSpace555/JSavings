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
		parentColumn = "account_name",
		entityColumn = "account_fk_name"
	)
	val purseEntities: List<PurseEntity>
) : BaseEntity
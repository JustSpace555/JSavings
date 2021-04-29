package ru.jsavings.data.mappers.database.binding

import ru.jsavings.data.entity.common.BaseEntity
import ru.jsavings.data.entity.database.binding.AccountWithPursesEntity
import ru.jsavings.data.mappers.common.BaseMapper
import ru.jsavings.data.mappers.database.AccountMapper
import ru.jsavings.data.mappers.database.PurseMapper
import ru.jsavings.data.model.database.binding.AccountWithPurses

internal class AccountWithPursesMapper(
	private val accountMapper: AccountMapper,
	private val purseMapper: PurseMapper
): BaseMapper<AccountWithPursesEntity, AccountWithPurses> {

	override fun mapEntityToModel(
		input: AccountWithPursesEntity,
		vararg additionalElements: BaseEntity
	): AccountWithPurses =
		AccountWithPurses(
			accountMapper.mapEntityToModel(input.accountEntity),
			purseMapper.mapEntityListToModelList(input.purseEntities)
		)

	override fun mapModelToEntity(input: AccountWithPurses): AccountWithPursesEntity =
		AccountWithPursesEntity(
			accountMapper.mapModelToEntity(input.account),
			purseMapper.mapModelListToEntityList(input.purses)
		)
}
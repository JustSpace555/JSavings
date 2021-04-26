package ru.jsavings.data.mappers.binding

import ru.jsavings.data.entity.common.BaseEntity
import ru.jsavings.data.entity.database.binding.AccountWithPursesEntity
import ru.jsavings.data.mappers.AccountMapper
import ru.jsavings.data.mappers.BaseMapper
import ru.jsavings.data.mappers.PurseMapper
import ru.jsavings.data.model.binding.AccountWithPurses

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
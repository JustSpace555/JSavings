package ru.jsavings.data.mappers

import ru.jsavings.data.entity.database.account.AccountEntity
import ru.jsavings.data.entity.common.BaseEntity
import ru.jsavings.data.entity.database.purse.PurseEntity
import ru.jsavings.data.model.purse.Purse
import ru.jsavings.data.model.purse.PurseCategoryType

internal class PurseMapper(
	private val accountMapper: AccountMapper
) : BaseMapper<PurseEntity, Purse> {

	override fun mapEntityToModel(input: PurseEntity, vararg additionalElements: BaseEntity): Purse =
		Purse(
			purseId = input.purseId,
			name = input.purseName,
			account = accountMapper.mapEntityToModel(additionalElements.filterIsInstance<AccountEntity>().first()),
			balance = input.balance,
			currency = input.currency,
			category = PurseCategoryType.valueOf(input.category),
			color = input.color,
			iconPath = input.iconPath
		)

	override fun mapModelToEntity(input: Purse): PurseEntity =
		PurseEntity(
			purseId = input.purseId,
			purseName = input.name,
			accountFkId = input.account.accountId,
			balance = input.balance,
			currency = input.currency,
			category = input.category.toString(),
			color = input.color,
			iconPath = input.iconPath
		)
}
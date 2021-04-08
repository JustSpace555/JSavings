package ru.jsavings.data.mappers

import ru.jsavings.data.entity.BaseEntity
import ru.jsavings.data.entity.PurseEntity
import ru.jsavings.data.model.BaseModel
import ru.jsavings.data.model.purse.Purse
import ru.jsavings.data.model.purse.PurseCategoryType

internal class PurseMapper : BaseMapper<PurseEntity, Purse> {

	override fun mapEntityToModel(input: PurseEntity, vararg additionalElements: BaseEntity): Purse =
		Purse(
			name = input.purseName,
			accountName = input.accountFkName,
			balance = input.balance,
			currency = input.currency,
			category = PurseCategoryType.valueOf(input.category),
			color = input.color,
			iconPath = input.iconPath
		)

	override fun mapModelToEntity(input: Purse): PurseEntity =
		PurseEntity(
			purseName = input.name,
			accountFkName = input.accountName,
			balance = input.balance,
			currency = input.currency,
			category = input.category.toString(),
			color = input.color,
			iconPath = input.iconPath
		)
}
package ru.jsavings.data.mappers

import ru.jsavings.data.entity.PurseEntity
import ru.jsavings.data.model.BaseModel
import ru.jsavings.data.model.purse.Purse
import ru.jsavings.data.model.purse.PurseCategoryType

internal class PurseMapper : BaseMapper<PurseEntity, Purse> {

	override fun mapEntityToModel(input: PurseEntity, vararg additionalElements: BaseModel): Purse =
		Purse(
			accountId = input.accountFkId,
			name = input.name,
			balance = input.balance,
			currency = input.currency,
			category = PurseCategoryType.valueOf(input.category),
			color = input.color,
			iconPath = input.iconPath
		)

	override fun mapModelToEntity(input: Purse, vararg additionalElementIds: Int): PurseEntity =
		PurseEntity(
			purseId = 0,
			accountFkId = input.accountId,
			name = input.name,
			balance = input.balance,
			currency = input.currency,
			category = input.category.toString(),
			color = input.color,
			iconPath = input.iconPath
		)
}
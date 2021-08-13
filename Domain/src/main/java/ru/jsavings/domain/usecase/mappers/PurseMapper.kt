package ru.jsavings.domain.usecase.mappers

import ru.jsavings.data.entity.AccountEntity
import ru.jsavings.data.entity.common.BaseEntity
import ru.jsavings.data.entity.WalletEntity
import ru.jsavings.domain.usecase.model.purse.Purse
import ru.jsavings.domain.usecase.model.purse.PurseCategoryType

internal class PurseMapper(
	private val accountMapper: AccountMapper
) : BaseMapper<WalletEntity, Purse> {

	override fun mapEntityToModel(input: WalletEntity, vararg additionalElements: BaseEntity): Purse =
		Purse(
			purseId = input.walletId,
			name = input.walletName,
			account = accountMapper.mapEntityToModel(additionalElements.filterIsInstance<AccountEntity>().first()),
			balance = input.balance,
			currency = input.currencyCode,
			category = PurseCategoryType.valueOf(input.category),
			color = input.color,
			iconPath = input.iconPath
		)

	override fun mapModelToEntity(input: Purse): WalletEntity =
		WalletEntity(
			walletId = input.purseId,
			walletName = input.name,
			accountFkId = input.account.accountId,
			balance = input.balance,
			currencyCode = input.currency,
			category = input.category.toString(),
			color = input.color,
			iconPath = input.iconPath
		)
}
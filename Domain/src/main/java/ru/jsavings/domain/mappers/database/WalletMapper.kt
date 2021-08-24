package ru.jsavings.domain.mappers.database

import ru.jsavings.data.entity.database.WalletEntity
import ru.jsavings.domain.mappers.common.DataBaseMapper
import ru.jsavings.domain.model.database.wallet.Wallet
import ru.jsavings.domain.model.database.wallet.WalletCategoryType

/**
 * Mapper for [WalletEntity] and [Wallet]
 *
 * @author JustSpace
 */
class WalletMapper : DataBaseMapper<WalletEntity, Wallet> {

	override fun mapEntityToModel(entity: WalletEntity): Wallet = Wallet(
		walletId = entity.walletId,
		name = entity.walletName,
		accountId = entity.accountFkId,
		balance = entity.balance,
		currency = entity.currencyCode,
		creditLimit = entity.creditLimit,
		paymentDay = entity.paymentDay,
		gracePeriod = entity.gracePeriod,
		interestRate = entity.interestRate,
		category = WalletCategoryType.valueOf(entity.category),
		color = entity.color,
		iconPath = entity.iconPath
	)

	override fun mapModelToEntity(model: Wallet): WalletEntity = WalletEntity(
		walletId = model.walletId,
		walletName = model.name,
		accountFkId = model.accountId,
		balance = model.balance,
		currencyCode = model.currency,
		category = model.category.toString(),
		creditLimit = model.creditLimit,
		paymentDay = model.paymentDay,
		gracePeriod = model.gracePeriod,
		interestRate = model.interestRate,
		color = model.color,
		iconPath = model.iconPath
	)
}
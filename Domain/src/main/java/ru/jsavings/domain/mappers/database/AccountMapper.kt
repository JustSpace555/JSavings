package ru.jsavings.domain.mappers.database

import ru.jsavings.data.entity.database.AccountEntity
import ru.jsavings.domain.mappers.common.DataBaseMapper
import ru.jsavings.domain.model.database.Account

/**
 * Mapper for [AccountEntity] and [Account]
 *
 * @author JustSpace
 */
class AccountMapper : DataBaseMapper<AccountEntity, Account> {

	override fun mapEntityToModel(entity: AccountEntity): Account = Account(
		accountId = entity.accountId,
		name = entity.accountName,
		mainCurrencyCode = entity.mainCurrencyCode,
		balanceInMainCurrency = entity.balanceInMainCurrency
	)

	override fun mapModelToEntity(model: Account): AccountEntity =
		AccountEntity(
			accountId = model.accountId,
			accountName = model.name,
			mainCurrencyCode = model.mainCurrencyCode,
			balanceInMainCurrency = model.balanceInMainCurrency
		)
}
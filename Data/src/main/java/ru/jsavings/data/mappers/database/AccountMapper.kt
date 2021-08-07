package ru.jsavings.data.mappers.database

import ru.jsavings.data.entity.common.BaseEntity
import ru.jsavings.data.entity.database.account.AccountEntity
import ru.jsavings.data.mappers.common.BaseDataBaseMapper
import ru.jsavings.data.model.database.Account

internal class AccountMapper : BaseDataBaseMapper<AccountEntity, Account> {

	override fun mapEntityToModel(input: AccountEntity, vararg additionalElements: BaseEntity): Account =
		Account(
			accountId = input.accountId,
			name = input.accountName,
			mainCurrencyCode = input.mainCurrencyCode,
			balanceInMainCurrency = input.balanceInMainCurrency
		)

	override fun mapModelToEntity(input: Account): AccountEntity =
		AccountEntity(
			accountId = input.accountId,
			accountName = input.name,
			mainCurrencyCode = input.mainCurrencyCode,
			balanceInMainCurrency = input.balanceInMainCurrency
		)
}
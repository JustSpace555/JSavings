package ru.jsavings.data.mappers

import ru.jsavings.data.entity.AccountEntity
import ru.jsavings.data.entity.BaseEntity
import ru.jsavings.data.model.Account
import java.util.*

internal class AccountMapper : BaseMapper<AccountEntity, Account> {

	override fun mapEntityToModel(input: AccountEntity, vararg additionalElements: BaseEntity): Account =
		Account(
			accountId = input.accountId,
			name = input.accountName,
			mainCurrency = Currency.getInstance(input.mainCurrency),
			balanceInMainCurrency = input.balanceInMainCurrency
		)

	override fun mapModelToEntity(input: Account): AccountEntity =
		AccountEntity(
			accountId = input.accountId,
			accountName = input.name,
			mainCurrency = input.mainCurrency.currencyCode,
			balanceInMainCurrency = input.balanceInMainCurrency
		)
}
package ru.jsavings.domain.usecase.mappers

import ru.jsavings.data.entity.AccountEntity
import ru.jsavings.data.entity.common.BaseEntity
import ru.jsavings.domain.usecase.model.Account
import java.util.*

internal class AccountMapper : BaseMapper<AccountEntity, Account> {

	override fun mapEntityToModel(input: AccountEntity, vararg additionalElements: BaseEntity): Account =
		Account(
			accountId = input.accountId,
			name = input.accountName,
			mainCurrency = Currency.getInstance(input.mainCurrencyCode),
			balanceInMainCurrency = input.balanceInMainCurrency
		)

	override fun mapModelToEntity(input: Account): AccountEntity =
		AccountEntity(
			accountId = input.accountId,
			accountName = input.name,
			mainCurrencyCode = input.mainCurrency.currencyCode,
			balanceInMainCurrency = input.balanceInMainCurrency
		)
}
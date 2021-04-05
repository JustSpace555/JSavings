package ru.jsavings.data.mappers

import ru.jsavings.data.entity.AccountEntity
import ru.jsavings.data.model.Account
import ru.jsavings.data.model.BaseModel
import java.util.*

internal class AccountMapper : BaseMapper<AccountEntity, Account> {

	override fun mapEntityToModel(input: AccountEntity, vararg additionalElements: BaseModel): Account =
		Account(
			name = input.name,
			mainCurrency = Currency.getInstance(input.mainCurrency),
			balanceInMainCurrency = input.balanceInMainCurrency
		)

	override fun mapModelToEntity(input: Account, vararg additionalElementIds: Int): AccountEntity =
		AccountEntity(
			accountId = 0,
			name = input.name,
			mainCurrency = input.mainCurrency.currencyCode,
			balanceInMainCurrency = input.balanceInMainCurrency
		)
}
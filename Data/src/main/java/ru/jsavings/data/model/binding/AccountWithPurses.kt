package ru.jsavings.data.model.binding

import ru.jsavings.data.model.Account
import ru.jsavings.data.model.BaseModel
import ru.jsavings.data.model.purse.Purse

data class AccountWithPurses (
	val account: Account,
	val purses: List<Purse>
) : BaseModel
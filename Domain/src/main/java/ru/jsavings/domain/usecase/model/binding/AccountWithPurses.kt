package ru.jsavings.domain.usecase.model.binding

import ru.jsavings.domain.usecase.model.Account
import ru.jsavings.domain.usecase.model.BaseModel
import ru.jsavings.domain.usecase.model.purse.Purse

data class AccountWithPurses (
	val account: Account,
	val purses: List<Purse>
) : BaseModel
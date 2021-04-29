package ru.jsavings.data.model.database.binding

import ru.jsavings.data.model.database.Account
import ru.jsavings.data.model.common.BaseModel
import ru.jsavings.data.model.database.common.BaseDbModel
import ru.jsavings.data.model.database.purse.Purse

data class AccountWithPurses (
	val account: Account,
	val purses: List<Purse>
) : BaseDbModel
package ru.jsavings.data.repository.database.binding

import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.model.database.binding.AccountWithPurses
import ru.jsavings.data.repository.database.common.BaseDbRepository

interface AccountWithPursesRepository : BaseDbRepository {

	fun getAllAccountsWithPurses(): Single<List<AccountWithPurses>>
}
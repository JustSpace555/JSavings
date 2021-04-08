package ru.jsavings.data.repository.binding

import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.model.binding.AccountWithPurses
import ru.jsavings.data.repository.BaseRepository

interface AccountWithPursesRepository : BaseRepository {

	fun getAllAccountsWithPurses(): Single<List<AccountWithPurses>>
}
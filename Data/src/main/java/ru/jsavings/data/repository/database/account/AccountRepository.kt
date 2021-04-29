package ru.jsavings.data.repository.database.account

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.model.database.Account
import ru.jsavings.data.repository.database.common.BaseDbRepository

interface AccountRepository : BaseDbRepository {

	fun getAllAccounts(): Single<List<Account>>

	fun createNewAccount(account: Account): Completable

	fun updateAccount(account: Account): Completable

	fun deleteAccounts(accounts: List<Account>): Completable
}
package ru.jsavings.data.repository.account

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.entity.AccountEntity
import ru.jsavings.data.model.Account
import ru.jsavings.data.repository.BaseRepository

interface AccountRepository : BaseRepository<AccountEntity, Account> {

	fun getAllAccounts(): Single<List<Account>>

	fun createNewAccount(account: Account): Single<Int>

	fun updateAccount(account: Account): Completable

	fun deleteAccount(account: Account): Completable
}
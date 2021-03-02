package ru.jsavings.repository.entity.account

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.jsavings.model.entity.Account

interface AccountRepository {

	fun getAllAccounts(): Single<List<Account>>

	fun getAccountById(id: Int): Single<Account>

	fun updateAccount(account: Account): Completable

	fun deleteAccount(account: Account): Completable
}
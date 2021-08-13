package ru.jsavings.data.repository.account

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.jsavings.domain.usecase.model.Account
import ru.jsavings.data.repository.common.DbRepository

interface AccountRepository : DbRepository {

	fun getAllAccounts(): Single<List<ru.jsavings.domain.usecase.model.Account>>

	fun createNewAccount(account: ru.jsavings.domain.usecase.model.Account): Completable

	fun updateAccount(account: ru.jsavings.domain.usecase.model.Account): Completable

	fun deleteAccounts(accounts: List<ru.jsavings.domain.usecase.model.Account>): Completable
}
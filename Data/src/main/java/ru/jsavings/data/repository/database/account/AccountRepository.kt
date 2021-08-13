package ru.jsavings.data.repository.database.account

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
<<<<<<< refs/remotes/origin/dev:Data/src/main/java/ru/jsavings/data/repository/database/account/AccountRepository.kt
import ru.jsavings.data.model.database.Account
import ru.jsavings.data.repository.database.common.BaseDbRepository
=======
import ru.jsavings.domain.usecase.model.Account
import ru.jsavings.data.repository.common.DbRepository
>>>>>>> Rework started:Data/src/main/java/ru/jsavings/data/repository/account/AccountRepository.kt

interface AccountRepository : BaseDbRepository {

	fun getAllAccounts(): Single<List<ru.jsavings.domain.usecase.model.Account>>

<<<<<<< refs/remotes/origin/dev:Data/src/main/java/ru/jsavings/data/repository/database/account/AccountRepository.kt
	fun createNewAccount(account: Account): Single<Long>
=======
	fun createNewAccount(account: ru.jsavings.domain.usecase.model.Account): Completable
>>>>>>> Rework started:Data/src/main/java/ru/jsavings/data/repository/account/AccountRepository.kt

	fun updateAccount(account: ru.jsavings.domain.usecase.model.Account): Completable

<<<<<<< refs/remotes/origin/dev:Data/src/main/java/ru/jsavings/data/repository/database/account/AccountRepository.kt
	fun deleteAccounts(accounts: List<Account>): Completable

	fun getAccountById(id: Long): Single<Account>
=======
	fun deleteAccounts(accounts: List<ru.jsavings.domain.usecase.model.Account>): Completable
>>>>>>> Rework started:Data/src/main/java/ru/jsavings/data/repository/account/AccountRepository.kt
}
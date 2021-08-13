package ru.jsavings.data.repository.database.account

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.database.dao.AccountDao
<<<<<<< refs/remotes/origin/dev:Data/src/main/java/ru/jsavings/data/repository/database/account/AccountRepositoryImpl.kt
import ru.jsavings.data.mappers.database.AccountMapper
import ru.jsavings.data.model.database.Account
=======
import ru.jsavings.domain.usecase.mappers.AccountMapper
import ru.jsavings.domain.usecase.model.Account
import java.lang.Exception
>>>>>>> Rework started:Data/src/main/java/ru/jsavings/data/repository/account/AccountRepositoryImpl.kt

internal class AccountRepositoryImpl (
	override val dao: AccountDao,
	override val mapper: ru.jsavings.domain.usecase.mappers.AccountMapper
) : AccountRepository {

	override fun getAllAccounts(): Single<List<ru.jsavings.domain.usecase.model.Account>> = Single.create { subscriber ->
		try {
			subscriber.onSuccess(
				mapper.mapEntityListToModelList(dao.getAllAccounts())
			)
		} catch (e: Exception) {
			subscriber.onError(e)
		}
	}

<<<<<<< refs/remotes/origin/dev:Data/src/main/java/ru/jsavings/data/repository/database/account/AccountRepositoryImpl.kt
	override fun createNewAccount(account: Account): Single<Long> = Single.create { subscriber ->
		try {
			val id = dao.createNewAccount(mapper.mapModelToEntity(account))
			subscriber.onSuccess(id)
=======
	override fun createNewAccount(account: ru.jsavings.domain.usecase.model.Account): Completable = Completable.create { subscriber ->
		try {
			dao.insertNewAccount(mapper.mapModelToEntity(account))
			subscriber.onComplete()
>>>>>>> Rework started:Data/src/main/java/ru/jsavings/data/repository/account/AccountRepositoryImpl.kt
		} catch (e: Exception) {
			subscriber.onError(e)
		}
	}

	override fun updateAccount(account: ru.jsavings.domain.usecase.model.Account): Completable = Completable.create { subscriber ->
		try {
			dao.updateAccount(mapper.mapModelToEntity(account))
			subscriber.onComplete()
		} catch (e: Exception) {
			subscriber.onError(e)
		}
	}

	override fun deleteAccounts(accounts: List<ru.jsavings.domain.usecase.model.Account>): Completable = Completable.create { subscriber ->
		try {
			dao.deleteAccountById(mapper.mapModelListToEntityList(accounts))
			subscriber.onComplete()
		} catch (e: Exception) {
			subscriber.onError(e)
		}
	}

	override fun getAccountById(id: Long): Single<Account> = Single.create { subscriber ->
		try {
			val account = mapper.mapEntityToModel(dao.getAccountById(id))
			subscriber.onSuccess(account)
		} catch (e: Exception) {
			subscriber.onError(e)
		}
	}
}
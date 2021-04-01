package ru.jsavings.data.repository.account

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.database.dao.AccountDao
import ru.jsavings.data.database.dao.BaseDao
import ru.jsavings.data.entity.AccountEntity
import ru.jsavings.data.mappers.AccountMapper
import ru.jsavings.data.mappers.BaseMapper
import ru.jsavings.data.model.Account
import java.lang.Exception

class AccountRepositoryImpl(
	override val dao: AccountDao,
	override val mapper: AccountMapper
) : AccountRepository {

	override fun getAllAccounts(): Single<List<Account>> = Single.create { subscriber ->
		try {
			subscriber.onSuccess(
				mapper.mapEntityListToModelList(dao.getAllAccounts())
			)
		} catch (e: Exception) {
			subscriber.onError(e)
		}
	}

	override fun createNewAccount(account: Account): Single<Int> = Single.create { subscriber ->
		try {
			subscriber.onSuccess(
				dao.createNewAccount(mapper.mapModelToEntity(account))
			)
		} catch (e: Exception) {
			subscriber.onError(e)
		}
	}

	override fun updateAccount(account: Account): Completable = Completable.create { subscriber ->
		try {
			dao.updateAccount(mapper.mapModelToEntity(account))
			subscriber.onComplete()
		} catch (e: Exception) {
			subscriber.onError(e)
		}
	}

	override fun deleteAccount(account: Account): Completable = Completable.create { subscriber ->
		try {
			dao.deleteAccount(mapper.mapModelToEntity(account))
			subscriber.onComplete()
		} catch (e: Exception) {
			subscriber.onError(e)
		}
	}
}
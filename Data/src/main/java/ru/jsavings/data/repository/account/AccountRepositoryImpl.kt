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

internal class AccountRepositoryImpl (
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

	override fun createNewAccount(account: Account): Completable = Completable.create { subscriber ->
		try {
			dao.createNewAccount(mapper.mapModelToEntity(account))
			subscriber.onComplete()
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

	override fun deleteAccounts(accounts: List<Account>): Completable = Completable.create { subscriber ->
		try {
			dao.deleteAccounts(mapper.mapModelListToEntityList(accounts))
			subscriber.onComplete()
		} catch (e: Exception) {
			subscriber.onError(e)
		}
	}
}
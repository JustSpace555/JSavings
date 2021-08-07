package ru.jsavings.data.repository.database.account

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.database.dao.AccountDao
import ru.jsavings.data.mappers.database.AccountMapper
import ru.jsavings.data.model.database.Account

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

	override fun createNewAccount(account: Account): Single<Long> = Single.create { subscriber ->
		try {
			val id = dao.createNewAccount(mapper.mapModelToEntity(account))
			subscriber.onSuccess(id)
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

	override fun getAccountById(id: Long): Single<Account> = Single.create { subscriber ->
		try {
			val account = mapper.mapEntityToModel(dao.getAccountById(id))
			subscriber.onSuccess(account)
		} catch (e: Exception) {
			subscriber.onError(e)
		}
	}
}
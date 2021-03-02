package ru.jsavings.repository.entity.account

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.jsavings.database.dao.AccountDao
import ru.jsavings.model.entity.Account
import java.lang.Exception

class AccountRepositoryImpl(private val accountDao: AccountDao) : AccountRepository {

	override fun getAllAccounts(): Single<List<Account>> = Single.create { subscriber ->
		try {
			subscriber.onSuccess(accountDao.getAllAccounts())
		} catch (e: Exception) {
			subscriber.onError(e)
		}
	}

	override fun getAccountById(id: Int): Single<Account> = Single.create { subscriber ->
		try {
			subscriber.onSuccess(accountDao.getAccountById(id))
		} catch (e: Exception) {
			subscriber.onError(e)
		}
	}

	override fun updateAccount(account: Account): Completable = Completable.create { subscriber ->
		try {
			accountDao.updateAccount(account)
			subscriber.onComplete()
		} catch (e: Exception) {
			subscriber.onError(e)
		}
	}

	override fun deleteAccount(account: Account): Completable = Completable.create { subscriber ->
		try {
			accountDao.deleteAccount(account)
			subscriber.onComplete()
		} catch (e: Exception) {
			subscriber.onError(e)
		}
	}
}
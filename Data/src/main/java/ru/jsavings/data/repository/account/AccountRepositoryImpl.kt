package ru.jsavings.data.repository.account

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.database.dao.AccountDao
import ru.jsavings.domain.usecase.mappers.AccountMapper
import ru.jsavings.domain.usecase.model.Account
import java.lang.Exception

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

	override fun createNewAccount(account: ru.jsavings.domain.usecase.model.Account): Completable = Completable.create { subscriber ->
		try {
			dao.insertNewAccount(mapper.mapModelToEntity(account))
			subscriber.onComplete()
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
}
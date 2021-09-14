package ru.jsavings.data.repository.database.account

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.database.dao.AccountDao
import ru.jsavings.data.entity.database.AccountEntity

/**
 * Implementation of [AccountRepository]
 * @param dao [AccountDao] to get data from
 *
 * @author JustSpace
 */
internal class AccountRepositoryImpl(override val dao: AccountDao) : AccountRepository {

	override fun getAllAccounts(): Single<List<AccountEntity>> = dao.getAllAccounts()

	override fun getAccountByIdFlowable(accountId: Long): Flowable<AccountEntity> = dao.getAccountByIdFlowable(accountId)

	override fun getAccountByIdSingle(accountId: Long): Single<AccountEntity> = dao.getAccountByIdSingle(accountId)

	override fun insertNewAccount(accountEntity: AccountEntity): Single<Long> = dao.insertNewAccount(accountEntity)

	override fun updateAccount(accountEntity: AccountEntity): Completable = dao.updateAccount(accountEntity)

	override fun deleteAccount(accountEntity: AccountEntity): Completable = dao.deleteAccount(accountEntity)
}
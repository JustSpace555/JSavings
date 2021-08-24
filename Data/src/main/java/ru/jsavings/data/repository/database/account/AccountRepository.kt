package ru.jsavings.data.repository.database.account

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.entity.database.AccountEntity
import ru.jsavings.data.repository.database.common.BaseDbRepository

/**
 * Repository that implements all [ru.jsavings.data.database.dao.AccountDao] requests
 * @author JustSpace
 */
interface AccountRepository : BaseDbRepository {

	/**
	 * Get all accounts from accounts' table
	 * @return [Single] with list of all [AccountEntity] in account's table
	 *
	 * @author JustSpace
	 */
	fun getAllAccounts(): Single<List<AccountEntity>>

	/**
	 * Get account from accounts' table by it id
	 * @param accountId Account id
	 * @return [Single] with [AccountEntity] which id is equal to [accountId]
	 *
	 * @author JustSpace
	 */
	fun getAccountById(accountId: Long): Single<AccountEntity>

	/**
	 * Insert new account into accounts' table
	 * @param accountEntity Account entity to insert
	 * @return [Single] with new account's id
	 *
	 * @author JustSpace
	 */
	fun insertNewAccount(accountEntity: AccountEntity): Single<Long>

	/**
	 * Update account in accounts' table
	 * @param accountEntity [AccountEntity] with id which must be updated
	 * @return [Completable] source of action
	 *
	 * @author JustSpace
	 */
	fun updateAccount(accountEntity: AccountEntity): Completable

	/**
	 * Delete account from accounts' table
	 * @param accountEntity [AccountEntity] which must be deleted
	 * @return [Completable] source of action
	 *
	 * @author JustSpace
	 */
	fun deleteAccount(accountEntity: AccountEntity): Completable
}
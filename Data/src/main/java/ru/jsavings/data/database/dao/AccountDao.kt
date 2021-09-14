package ru.jsavings.data.database.dao

import androidx.room.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.entity.database.AccountEntity

/**
 * Data access object for accounts' table
 *
 * @author JustSpace
 */
@Dao
internal interface AccountDao : BaseDao {

	/**
	 * Get all accounts from accounts' table
	 * @return [Single] with list of all [AccountEntity] in account's table
	 *
	 * @author JustSpace
	 */
	@Query("SELECT * FROM account_table")
	fun getAllAccounts(): Single<List<AccountEntity>>

	/**
	 * Get account from accounts' table by it id
	 * @param accountId Account id
	 * @return [Flowable] with [AccountEntity] which id is equal to [accountId]. [Flowable] is needed for live updates
	 * of current account in app
	 *
	 * @author JustSpace
	 */
	@Query("SELECT * FROM account_table WHERE account_id = :accountId")
	fun getAccountByIdFlowable(accountId: Long): Flowable<AccountEntity>

	/**
	 * Get account from account's table by it id. This method must be used only for DOMAIN layer to get one time info
	 * about account with id = [accountId]
	 * @param accountId
	 * @return [Single] source with [AccountEntity].
	 * @author JustSpace
	 */
	@Query("SELECT * FROM account_table WHERE account_id = :accountId")
	fun getAccountByIdSingle(accountId: Long): Single<AccountEntity>

	/**
	 * Insert new account into accounts' table
	 * @param accountEntity Account entity to insert
	 * @return [Single] with new account's id
	 *
	 * @author JustSpace
	 */
	@Insert
	fun insertNewAccount(accountEntity: AccountEntity): Single<Long>

	/**
	 * Update account in accounts' table
	 * @param accountEntity [AccountEntity] with id which must be updated
	 * @return [Completable] source of action
	 *
	 * @author JustSpace
	 */
	@Update
	fun updateAccount(accountEntity: AccountEntity): Completable

	/**
	 * Delete account from accounts' table
	 * @param accountEntity [AccountEntity] which must be deleted
	 * @return [Completable] source of action
	 *
	 * @author JustSpace
	 */
	@Delete
	fun deleteAccount(accountEntity: AccountEntity): Completable
}
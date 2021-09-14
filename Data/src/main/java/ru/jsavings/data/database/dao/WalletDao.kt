package ru.jsavings.data.database.dao

import androidx.room.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.entity.database.WalletEntity

/**
 * Data access object for wallets' table
 *
 * @author JustSpace
 */
@Dao
internal interface WalletDao : BaseDao {

	/**
	 * Get all wallets which relate to account with id = [accountId]
	 * @param accountId Account's personal id
	 * @return [Single] with list of wallets
	 *
	 * @author JustSpace
	 */
	@Query("SELECT * FROM wallet_table WHERE account_fk_id = :accountId")
	fun getWalletsByAccountId(accountId: Long): Single<List<WalletEntity>>

	/**
	 * Get wallet from wallets' table by id
	 * @param walletId Id of wallet
	 * @return [Single] source with [WalletEntity]
	 *
	 * @author JustSpace
	 */
	@Query("SELECT * FROM wallet_table WHERE wallet_id = :walletId")
	fun getWalletById(walletId: Long): Single<WalletEntity>

	/**
	 * Insert new wallet to wallets' table
	 * @param walletEntity [WalletEntity] to insert
	 * @return [Completable] source of action
	 *
	 * @author JustSpace
	 */
	@Insert
	fun insertNewWallet(walletEntity: WalletEntity): Single<Long>

	/**
	 * Update wallet in wallets' table
	 * @param walletEntity [WalletEntity] with id to update
	 * @return [Completable] source of action
	 *
	 * @author JustSpace
	 */
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun updateWallet(walletEntity: WalletEntity): Completable

	/**
	 * Delete wallet by id from wallet's table
	 * @param walletEntity [WalletEntity] which must be deleted
	 * @return [Completable] source of action
	 *
	 * @author JustSpace
	 */
	@Delete
	fun deleteWallet(walletEntity: WalletEntity): Completable
}
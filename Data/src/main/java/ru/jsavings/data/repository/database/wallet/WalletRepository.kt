package ru.jsavings.data.repository.database.wallet

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.entity.database.WalletEntity
import ru.jsavings.data.repository.database.common.BaseDbRepository

/**
 * Repository that implements all [ru.jsavings.data.database.dao.WalletDao] requests
 * @author JustSpace
 */
interface WalletRepository : BaseDbRepository {

	/**
	 * Get all wallets which relate to account with id = [accountId]
	 * @param accountId Account's personal id
	 * @return [Single] with list of wallets
	 *
	 * @author JustSpace
	 */
	fun getWalletsByAccountId(accountId: Long): Single<List<WalletEntity>>

	/**
	 * Get wallet from wallets' table by id
	 * @param walletId Id of wallet
	 * @return [Single] source with [WalletEntity]
	 *
	 * @author JustSpace
	 */
	fun getWalletById(walletId: Long): Single<WalletEntity>

	/**
	 * Insert new wallet to wallets' table
	 * @param walletEntity [WalletEntity] to insert
	 * @return [Completable] source of action
	 *
	 * @author JustSpace
	 */
	fun insertNewWallet(walletEntity: WalletEntity): Single<Long>

	/**
	 * Update wallet in wallets' table
	 * @param walletEntity [WalletEntity] with id to update
	 * @return [Completable] source of action
	 *
	 * @author JustSpace
	 */
	fun updateWallet(walletEntity: WalletEntity): Completable

	/**
	 * Delete wallet by id from wallet's table
	 * @param walletEntity [WalletEntity] which must be deleted
	 * @return [Completable] source of action
	 *
	 * @author JustSpace
	 */
	fun deleteWallet(walletEntity: WalletEntity): Completable
}
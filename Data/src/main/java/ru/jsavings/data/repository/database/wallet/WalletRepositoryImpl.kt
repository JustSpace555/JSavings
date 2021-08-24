package ru.jsavings.data.repository.database.wallet

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.database.dao.WalletDao
import ru.jsavings.data.entity.database.WalletEntity

/**
 * Implementation of [WalletRepository]
 * @param dao [WalletDao] to get data from
 *
 * @author JustSpace
 */
internal class WalletRepositoryImpl(override val dao: WalletDao) : WalletRepository {

	override fun getWalletsByAccountId(accountId: Long): Single<List<WalletEntity>> =
		dao.getWalletsByAccountId(accountId)

	override fun getWalletById(walletId: Long): Single<WalletEntity> =
		dao.getWalletById(walletId)

	override fun insertNewWallet(walletEntity: WalletEntity): Single<Long> =
		dao.insertNewWallet(walletEntity)

	override fun updateWallet(walletEntity: WalletEntity): Completable =
		dao.updateWallet(walletEntity)

	override fun deleteWallet(walletEntity: WalletEntity): Completable =
		dao.deleteWallet(walletEntity)
}
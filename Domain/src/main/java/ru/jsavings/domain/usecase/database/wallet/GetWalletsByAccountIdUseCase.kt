package ru.jsavings.domain.usecase.database.wallet

import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.repository.database.wallet.WalletRepository
import ru.jsavings.domain.mappers.database.WalletMapper
import ru.jsavings.domain.model.database.wallet.Wallet
import ru.jsavings.domain.usecase.common.BaseUseCase

/**
 * Get wallets by account id from database
 * @param repository [WalletRepository] to interact with wallets' table
 * @param mapper [WalletMapper] to map entities to models
 *
 * @author Михаил Мошков
 */
class GetWalletsByAccountIdUseCase(
	private val repository: WalletRepository,
	private val mapper: WalletMapper
) : BaseUseCase {

	/**
	 * Invoke usecase
	 * @param accountId Id of account to get wallets associated with
	 * @return [Single] source with list of wallets
	 *
	 * @author Михаил Мошков
	 */
	operator fun invoke(accountId: Long): Single<List<Wallet>> = repository.getWalletsByAccountId(accountId)
		.map { walletsList ->
			walletsList.map { walletEntity -> mapper.mapEntityToModel(walletEntity) }
		}
}
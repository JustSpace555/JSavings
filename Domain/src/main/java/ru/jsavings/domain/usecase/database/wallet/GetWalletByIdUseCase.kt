package ru.jsavings.domain.usecase.database.wallet

import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.repository.database.wallet.WalletRepository
import ru.jsavings.domain.mappers.database.WalletMapper
import ru.jsavings.domain.model.database.wallet.Wallet
import ru.jsavings.domain.usecase.common.BaseUseCase

/**
 * Get Wallet from database by it's id
 * @param walletRepository [WalletRepository] to interact with wallets' table
 * @param mapper Mapper to map entity to model
 *
 * @author JustSpace
 */
class GetWalletByIdUseCase(
	private val walletRepository: WalletRepository,
	private val mapper: WalletMapper
) : BaseUseCase() {

	/**
	 * Invokes usecase
	 * @param walletId Id of wallet
	 * @return [Single] source of [Wallet]
	 *
	 * @author JustSpace
	 */
	operator fun invoke(walletId: Long): Single<Wallet> = walletRepository.getWalletById(walletId).map {
		mapper.mapEntityToModel(it)
	}
}
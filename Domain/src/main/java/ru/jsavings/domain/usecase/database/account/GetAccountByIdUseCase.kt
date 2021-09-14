package ru.jsavings.domain.usecase.database.account

import io.reactivex.rxjava3.core.Flowable
import ru.jsavings.data.repository.database.account.AccountRepository
import ru.jsavings.domain.mappers.database.AccountMapper
import ru.jsavings.domain.model.database.Account
import ru.jsavings.domain.usecase.common.BaseUseCase

/**
 * Get account from database by it's id
 * @param repository [AccountRepository] to interact with account's table
 * @param mapper [AccountMapper] to map entity to model
 *
 * @author Михаил Мошков
 */
class GetAccountByIdUseCase(
	private val repository: AccountRepository,
	private val mapper: AccountMapper
) : BaseUseCase {

	/**
	 * Invoke usecase
	 * @param accountId Id of account to get
	 * @return [Flowable] source of [Account]. Flowable is needed for live updates of current account
	 *
	 * @author Михаил Мошков
	 */
	operator fun invoke(accountId: Long): Flowable<Account> = repository.getAccountByIdFlowable(accountId).map {
			entity -> mapper.mapEntityToModel(entity)
	}
}
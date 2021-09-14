package ru.jsavings.domain.usecase.database.account

import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.repository.database.account.AccountRepository
import ru.jsavings.domain.mappers.database.AccountMapper
import ru.jsavings.domain.model.database.Account
import ru.jsavings.domain.usecase.common.BaseUseCase

/**
 * Use case for inserting new account in database
 * @param repository [AccountRepository] to interact with database
 * @param mapper [AccountMapper] to map [Account] to [ru.jsavings.data.entity.database.AccountEntity]
 *
 * @author JustSpace
 */
class InsertAccountUseCase(
	private val repository: AccountRepository,
	private val mapper: AccountMapper
) : BaseUseCase {

	/**
	 * Executing usecase
	 * @param account [Account] to insert in database
	 * @return [Single] source of inserted account id [Long]
	 *
	 * @author JustSpace
	 */
	operator fun invoke(account: Account): Single<Long> = repository.insertNewAccount(
		mapper.mapModelToEntity(account)
	)
}
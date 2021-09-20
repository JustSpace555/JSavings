package ru.jsavings.domain.usecase.database.account

import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.repository.database.account.AccountRepository
import ru.jsavings.domain.mappers.database.AccountMapper
import ru.jsavings.domain.model.database.Account
import ru.jsavings.domain.usecase.common.BaseUseCase

/**
 * Use case for getting all accounts from database
 * @param repository [AccountRepository] to interact with database
 * @param mapper [AccountMapper] to map [ru.jsavings.data.entity.database.AccountEntity] to [Account]
 *
 * @author JustSpace
 */
class GetAllAccountsUseCase(
	private val repository: AccountRepository,
	private val mapper: AccountMapper
) : BaseUseCase() {

	/**
	 * Executing usecase
	 * @return [Single] source with list [Account]
	 *
	 * @author JustSpace
	 */
	operator fun invoke(): Single<List<Account>> = repository
		.getAllAccounts()
		.map { list ->
			list.map { accountEntity ->  mapper.mapEntityToModel(accountEntity) }
		}
}
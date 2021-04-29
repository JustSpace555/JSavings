package ru.jsavings.data.repository.database.binding

import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.database.dao.binding.AccountWithPursesDao
import ru.jsavings.data.mappers.database.binding.AccountWithPursesMapper
import ru.jsavings.data.model.database.binding.AccountWithPurses
import java.lang.Exception

internal class AccountWithPursesRepositoryImpl(
	override val dao: AccountWithPursesDao,
	override val mapper: AccountWithPursesMapper
) : AccountWithPursesRepository {

	override fun getAllAccountsWithPurses(): Single<List<AccountWithPurses>> = Single.create { subscriber ->
		try {
			subscriber.onSuccess(
				mapper.mapEntityListToModelList(dao.getAllAccountsWithPurses())
			)
		} catch (e: Exception) {
			subscriber.onError(e)
		}
	}
}
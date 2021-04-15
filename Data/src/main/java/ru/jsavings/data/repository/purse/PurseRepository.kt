package ru.jsavings.data.repository.purse

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.model.purse.Purse
import ru.jsavings.data.repository.common.DbRepository

interface PurseRepository : DbRepository {

	fun getPurseByName(inputName: String): Single<Purse>

	fun getPursesByAccountName(accountName: String): Single<List<Purse>>

	fun addNewPurse(purse: Purse): Completable

	fun updatePurse(purse: Purse): Completable

	fun deletePurse(purse: Purse): Completable
}
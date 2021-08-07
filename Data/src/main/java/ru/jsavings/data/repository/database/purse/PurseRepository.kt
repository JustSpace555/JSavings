package ru.jsavings.data.repository.database.purse

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.model.database.purse.Purse
import ru.jsavings.data.repository.database.common.BaseDbRepository

interface PurseRepository : BaseDbRepository {

	fun addNewPurse(purse: Purse): Single<Long>

	fun updatePurse(purse: Purse): Completable

	fun deletePurse(purse: Purse): Completable
}
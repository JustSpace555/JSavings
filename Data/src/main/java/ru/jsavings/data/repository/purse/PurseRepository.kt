package ru.jsavings.data.repository.purse

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.entity.Purse

interface PurseRepository {

	fun getPurseById(id: Int): Single<Purse>

	fun getPursesByIdList(ids: List<Int>): Single<List<Purse>>

	fun addNewPurse(purse: Purse): Completable

	fun updatePurse(purse: Purse): Completable

	fun deletePurse(purse: Purse): Completable
}
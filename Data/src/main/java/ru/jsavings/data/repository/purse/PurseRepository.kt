package ru.jsavings.data.repository.purse

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.entity.PurseEntity
import ru.jsavings.data.model.purse.Purse
import ru.jsavings.data.repository.BaseRepository

interface PurseRepository : BaseRepository<PurseEntity, Purse> {

	fun getPurseById(id: Int): Single<Purse>

	fun addNewPurse(purse: Purse): Single<Int>

	fun updatePurse(purse: Purse): Completable

	fun deletePurse(purse: Purse): Completable
}
package ru.jsavings.data.repository.purse

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.database.dao.PurseDao
import ru.jsavings.data.entity.PurseEntity
import ru.jsavings.data.mappers.PurseMapper
import ru.jsavings.data.model.purse.Purse
import java.lang.Exception

class PurseRepositoryImpl (
	override val dao: PurseDao,
	override val mapper: PurseMapper
) : PurseRepository {

	override fun getPurseById(id: Int): Single<Purse> = Single.create { subscriber ->
		try {
			subscriber.onSuccess(
				mapper.mapEntityToModel(dao.getPurseById(id))
			)
		} catch (e: Exception) {
			subscriber.onError(e)
		}
	}

	override fun addNewPurse(purse: Purse): Single<Int> = Single.create { subscriber ->
		try {
			subscriber.onSuccess(
				dao.addNewPurse(mapper.mapModelToEntity(purse))
			)
		} catch (e: Exception) {
			subscriber.onError(e)
		}
	}

	override fun updatePurse(purse: Purse): Completable = Completable.create { subscriber ->
		try {
			dao.updatePurse(mapper.mapModelToEntity(purse))
			subscriber.onComplete()
		} catch (e: Exception) {
			subscriber.onError(e)
		}
	}

	override fun deletePurse(purse: Purse): Completable = Completable.create { subscriber ->
		try {
			dao.deletePurse(mapper.mapModelToEntity(purse))
			subscriber.onComplete()
		} catch (e: Exception) {
			subscriber.onError(e)
		}
	}
}
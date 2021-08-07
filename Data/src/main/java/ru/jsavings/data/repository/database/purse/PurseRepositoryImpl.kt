package ru.jsavings.data.repository.database.purse

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.database.dao.PurseDao
import ru.jsavings.data.mappers.database.PurseMapper
import ru.jsavings.data.model.database.purse.Purse

internal class PurseRepositoryImpl (
	override val dao: PurseDao,
	override val mapper: PurseMapper
) : PurseRepository {

	override fun addNewPurse(purse: Purse): Single<Long> = Single.create { subscriber ->
		try {
			val id = dao.addNewPurse(mapper.mapModelToEntity(purse))
			subscriber.onSuccess(id)
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
package ru.jsavings.data.implementations

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.database.dao.PurseDao
import ru.jsavings.data.entities.Purse
import ru.jsavings.data.model.PurseRepository
import java.lang.Exception

class PurseRepositoryImpl(private val purseDao: PurseDao) : PurseRepository {

	override fun getPurseById(id: Int): Single<Purse> = Single.create { subscriber ->
		try {
			subscriber.onSuccess(purseDao.getPurseById(id))
		} catch (e: Exception) {
			subscriber.onError(e)
		}
	}

	override fun getPursesByIdList(ids: List<Int>): Single<List<Purse>> = Single.create { subscriber ->
		try {
			subscriber.onSuccess(purseDao.getPursesByIdList(ids))
		} catch (e: Exception) {
			subscriber.onError(e)
		}
	}

	override fun addNewPurse(purse: Purse): Completable = Completable.create { subscriber ->
		try {
			purseDao.addNewPurse(purse)
			subscriber.onComplete()
		} catch (e: Exception) {
			subscriber.onError(e)
		}
	}

	override fun updatePurse(purse: Purse): Completable = Completable.create { subscriber ->
		try {
			purseDao.updatePurse(purse)
			subscriber.onComplete()
		} catch (e: Exception) {
			subscriber.onError(e)
		}
	}

	override fun deletePurse(purse: Purse): Completable = Completable.create { subscriber ->
		try {
			purseDao.deletePurse(purse)
			subscriber.onComplete()
		} catch (e: Exception) {
			subscriber.onError(e)
		}
	}
}
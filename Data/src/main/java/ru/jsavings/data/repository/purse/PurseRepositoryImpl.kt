package ru.jsavings.data.repository.purse

import io.reactivex.rxjava3.core.Completable
import ru.jsavings.data.database.dao.WalletDao
import ru.jsavings.domain.usecase.mappers.PurseMapper
import ru.jsavings.domain.usecase.model.purse.Purse

internal class PurseRepositoryImpl (
	override val dao: WalletDao,
	override val mapper: ru.jsavings.domain.usecase.mappers.PurseMapper
) : PurseRepository {

	override fun addNewPurse(purse: ru.jsavings.domain.usecase.model.purse.Purse): Completable = Completable.create { subscriber ->
		try {
			dao.insertNewPurse(mapper.mapModelToEntity(purse))
			subscriber.onComplete()
		} catch (e: Exception) {
			subscriber.onError(e)
		}
	}

	override fun updatePurse(purse: ru.jsavings.domain.usecase.model.purse.Purse): Completable = Completable.create { subscriber ->
		try {
			dao.updatePurse(mapper.mapModelToEntity(purse))
			subscriber.onComplete()
		} catch (e: Exception) {
			subscriber.onError(e)
		}
	}

	override fun deletePurse(purse: ru.jsavings.domain.usecase.model.purse.Purse): Completable = Completable.create { subscriber ->
		try {
			dao.deletePurseById(mapper.mapModelToEntity(purse))
			subscriber.onComplete()
		} catch (e: Exception) {
			subscriber.onError(e)
		}
	}
}
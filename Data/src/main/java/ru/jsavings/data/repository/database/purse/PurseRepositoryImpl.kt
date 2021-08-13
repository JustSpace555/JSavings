package ru.jsavings.data.repository.database.purse

import io.reactivex.rxjava3.core.Completable
<<<<<<< HEAD:Data/src/main/java/ru/jsavings/data/repository/database/purse/PurseRepositoryImpl.kt
<<<<<<< refs/remotes/origin/dev:Data/src/main/java/ru/jsavings/data/repository/database/purse/PurseRepositoryImpl.kt
import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.database.dao.PurseDao
import ru.jsavings.data.mappers.database.PurseMapper
import ru.jsavings.data.model.database.purse.Purse
=======
import ru.jsavings.data.database.dao.WalletDao
import ru.jsavings.domain.usecase.mappers.PurseMapper
import ru.jsavings.domain.usecase.model.purse.Purse
>>>>>>> Rework started:Data/src/main/java/ru/jsavings/data/repository/purse/PurseRepositoryImpl.kt
=======
import ru.jsavings.data.database.dao.WalletDao
import ru.jsavings.domain.usecase.mappers.PurseMapper
import ru.jsavings.domain.usecase.model.purse.Purse
>>>>>>> main:Data/src/main/java/ru/jsavings/data/repository/purse/PurseRepositoryImpl.kt

internal class PurseRepositoryImpl (
	override val dao: WalletDao,
	override val mapper: ru.jsavings.domain.usecase.mappers.PurseMapper
) : PurseRepository {

<<<<<<< HEAD:Data/src/main/java/ru/jsavings/data/repository/database/purse/PurseRepositoryImpl.kt
<<<<<<< refs/remotes/origin/dev:Data/src/main/java/ru/jsavings/data/repository/database/purse/PurseRepositoryImpl.kt
	override fun addNewPurse(purse: Purse): Single<Long> = Single.create { subscriber ->
		try {
			val id = dao.addNewPurse(mapper.mapModelToEntity(purse))
			subscriber.onSuccess(id)
=======
	override fun addNewPurse(purse: ru.jsavings.domain.usecase.model.purse.Purse): Completable = Completable.create { subscriber ->
		try {
=======
	override fun addNewPurse(purse: ru.jsavings.domain.usecase.model.purse.Purse): Completable = Completable.create { subscriber ->
		try {
>>>>>>> main:Data/src/main/java/ru/jsavings/data/repository/purse/PurseRepositoryImpl.kt
			dao.insertNewPurse(mapper.mapModelToEntity(purse))
			subscriber.onComplete()
>>>>>>> Rework started:Data/src/main/java/ru/jsavings/data/repository/purse/PurseRepositoryImpl.kt
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
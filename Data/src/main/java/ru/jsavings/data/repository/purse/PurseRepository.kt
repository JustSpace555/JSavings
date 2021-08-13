package ru.jsavings.data.repository.purse

import io.reactivex.rxjava3.core.Completable
import ru.jsavings.domain.usecase.model.purse.Purse
import ru.jsavings.data.repository.common.DbRepository

interface PurseRepository : DbRepository {

	fun addNewPurse(purse: ru.jsavings.domain.usecase.model.purse.Purse): Completable

	fun updatePurse(purse: ru.jsavings.domain.usecase.model.purse.Purse): Completable

	fun deletePurse(purse: ru.jsavings.domain.usecase.model.purse.Purse): Completable
}
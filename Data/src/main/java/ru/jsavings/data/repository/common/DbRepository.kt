package ru.jsavings.data.repository.common

import ru.jsavings.data.database.dao.BaseDao
import ru.jsavings.domain.usecase.mappers.BaseMapper

interface DbRepository : BaseRepository {
	val dao: BaseDao
	val mapper: ru.jsavings.domain.usecase.mappers.BaseMapper<*, *>
}
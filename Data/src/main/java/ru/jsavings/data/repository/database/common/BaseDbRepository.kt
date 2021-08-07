package ru.jsavings.data.repository.database.common

import ru.jsavings.data.database.dao.BaseDao
import ru.jsavings.data.mappers.common.BaseDataBaseMapper
import ru.jsavings.data.repository.common.BaseRepository

interface BaseDbRepository : BaseRepository {
	val dao: BaseDao
	val mapper: BaseDataBaseMapper<*, *>
}
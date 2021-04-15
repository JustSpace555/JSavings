package ru.jsavings.data.repository.common

import ru.jsavings.data.database.dao.BaseDao
import ru.jsavings.data.mappers.BaseMapper

interface DbRepository : BaseRepository {
	val dao: BaseDao
	val mapper: BaseMapper<*, *>
}
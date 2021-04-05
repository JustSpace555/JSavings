package ru.jsavings.data.repository

import ru.jsavings.data.database.dao.BaseDao
import ru.jsavings.data.mappers.BaseMapper

interface BaseRepository {
	val dao: BaseDao
	val mapper: BaseMapper<*, *>
}
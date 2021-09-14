package ru.jsavings.data.repository.database.common

import ru.jsavings.data.database.dao.BaseDao
import ru.jsavings.data.repository.common.BaseRepository

/**
 * Interface for all database repositories
 *
 * @author JustSpace
 */
interface BaseDbRepository : BaseRepository {
	val dao: BaseDao
}
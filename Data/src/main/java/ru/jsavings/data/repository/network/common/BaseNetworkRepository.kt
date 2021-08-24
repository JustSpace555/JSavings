package ru.jsavings.data.repository.network.common

import ru.jsavings.data.network.common.BaseApi
import ru.jsavings.data.repository.common.BaseRepository

/**
 * Interface for all network repositories
 *
 * @author JustSpace
 */
interface BaseNetworkRepository : BaseRepository {

	val api: BaseApi
}
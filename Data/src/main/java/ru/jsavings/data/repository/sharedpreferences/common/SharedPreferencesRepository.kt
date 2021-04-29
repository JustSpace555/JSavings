package ru.jsavings.data.repository.sharedpreferences.common

import ru.jsavings.data.repository.common.BaseRepository
import kotlin.reflect.KClass

interface SharedPreferencesRepository : BaseRepository {

	fun <T> putValue(key: String, value: T)

	fun <T : Any> getValue(key: String, kClass: KClass<T>, defaultValue: T): T

	fun removeValue(key: String)
}
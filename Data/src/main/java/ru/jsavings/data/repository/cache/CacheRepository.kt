package ru.jsavings.data.repository.cache

import android.content.SharedPreferences
import ru.jsavings.data.repository.common.BaseRepository

/**
 * Repository for caching data
 * @param sp [SharedPreferences] where to store information
 *
 * @author JustSpace
 */
class CacheRepository(private val sp: SharedPreferences) : BaseRepository {

	/**
	 * Put data to cache
	 * @param key Key to associate value with
	 * @param value Data to put into cache
	 *
	 * @author JustSpace
	 */
	fun <T: Any> putValue(key: String, value: T) =
		with(sp.edit()) {
			when (value) {
				is Boolean -> putBoolean(key, value)
				is String -> putString(key, value)
				is Int -> putInt(key, value)
				is Long -> putLong(key, value)
				is Float -> putFloat(key, value)
				else -> throw IllegalArgumentException("Illegal argument type for caching")
			}
			apply()
		}

	/**
	 * Get data from cache
	 * @param key Key of value
	 * @param defaultValue Default value to return if there is no data by [key]
	 *
	 * @author JustSpace
	 */
	@Suppress("UNCHECKED_CAST")
	fun <T: Any> getValue(key: String, defaultValue: T): T = with(sp) {
			when (defaultValue) {
				is Boolean -> getBoolean(key, defaultValue) as T
				is String -> getString(key, defaultValue) as T
				is Int -> getInt(key, defaultValue) as T
				is Long -> getLong(key, defaultValue) as T
				is Float -> getFloat(key, defaultValue) as T
				else -> throw IllegalArgumentException("Illegal argument type for getting from cache")
			}
		}

	/**
	 * Delete data from cache
	 * @param key Key of value
	 *
	 * @author JustSpace
	 */
	fun deleteValue(key: String) =
		with(sp.edit()) {
			remove(key)
			apply()
		}
}
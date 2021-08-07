package ru.jsavings.data.repository.cache

import android.content.SharedPreferences
import ru.jsavings.data.repository.common.BaseRepository

class CacheRepository(val sp: SharedPreferences) : BaseRepository {

	@Suppress("UNCHECKED_CAST")
	fun <T: Any> putValue(key: CacheKeys, value: T) {

		val keyStr = key.toString()

		return with(sp.edit()) {
			when (value) {
				is Boolean -> putBoolean(keyStr, value)
				is String -> putString(keyStr, value)
				is Int -> putInt(keyStr, value)
				is Long -> putLong(keyStr, value)
				is Float -> putFloat(keyStr, value)
				is Set<*> -> putStringSet(keyStr, value as Set<String>)
				else -> throw IllegalArgumentException("Illegal argument type for caching")
			}
			apply()
		}
	}

	@Suppress("UNCHECKED_CAST")
	inline fun <reified T: Any> getValue(key: CacheKeys, defaultValue: T): T {

		val keyStr = key.toString()

		return with(sp) {
			when (T::class) {
				Boolean::class -> getBoolean(keyStr, defaultValue as Boolean) as T
				String::class -> getString(keyStr, defaultValue as String) as T
				Int::class -> getInt(keyStr, defaultValue as Int) as T
				Long::class -> getLong(keyStr, defaultValue as Long) as T
				Float::class -> getFloat(keyStr, defaultValue as Float) as T
				Set::class -> getStringSet(keyStr, defaultValue as Set<String>) as T
				else -> throw IllegalArgumentException("Illegal argument type for getting from cache")
			}
		}
	}

	fun deleteValue(key: CacheKeys) =
		with(sp.edit()) {
			remove(key.toString())
			apply()
		}
}

enum class CacheKeys {
	JS_CURRENT_ACCOUNT
}
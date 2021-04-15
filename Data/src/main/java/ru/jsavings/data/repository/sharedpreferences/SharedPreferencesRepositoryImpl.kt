package ru.jsavings.data.repository.sharedpreferences

import android.content.SharedPreferences
import kotlin.reflect.KClass

internal class SharedPreferencesRepositoryImpl(
	private val sharedPreferences: SharedPreferences
) : JsSharedPreferencesRepository, NewAccountSharedPreferencesRepository {

	override fun <T> putValue(key: String, value: T) {
		with(sharedPreferences.edit()) {
			when (value) {
				is Boolean -> putBoolean(key, value)
				is String -> putString(key, value)
				is Int -> putInt(key, value)
				is Long -> putLong(key, value)
				else -> putFloat(key, value as Float)
			}
			apply()
		}
	}

	@Suppress("UNCHECKED_CAST")
	override fun <T : Any> getValue(key: String, kClass: KClass<T>, defaultValue: T): T =
		with(sharedPreferences) {
			when (kClass) {
				Boolean::class -> getBoolean(key, defaultValue as Boolean) as T
				String::class -> getString(key, defaultValue as String) as T
				Int::class -> getInt(key, defaultValue as Int) as T
				Long::class -> getLong(key, defaultValue as Long) as T
				else -> getFloat(key, defaultValue as Float) as T
			}
		}

	override fun removeValue(key: String) {
		with(sharedPreferences.edit()) {
			remove(key)
			apply()
		}
	}
}
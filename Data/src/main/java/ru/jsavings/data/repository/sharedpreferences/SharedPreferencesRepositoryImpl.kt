package ru.jsavings.data.repository.sharedpreferences

import android.content.SharedPreferences
import kotlin.reflect.KClass

internal class SharedPreferencesRepositoryImpl(
	private val sharedPreferences: SharedPreferences
) : SharedPreferencesRepository {

	override fun <T: Any> putValue(key: String, value: T) {
		with(sharedPreferences.edit()) {
			when (value) {
				is Boolean -> putBoolean(key, value)
				is String -> putString(key, value)
				is Int -> putInt(key, value)
				is Long -> putLong(key, value)
				is Float -> putFloat(key, value)
			}
			apply()
		}
	}

	@Suppress("UNCHECKED_CAST")
	override fun <T : Any> getValue(key: String, defaultValue: T): T =
		with(sharedPreferences) {
			when (defaultValue) {
				is Boolean -> getBoolean(key, defaultValue) as T
				is String -> getString(key, defaultValue) as T
				is Int -> getInt(key, defaultValue) as T
				is Long -> getLong(key, defaultValue) as T
				is Float -> getFloat(key, defaultValue) as T
				else -> throw IllegalArgumentException("Illegal argument type")
			}
		}

	override fun removeValue(key: String) {
		with(sharedPreferences.edit()) {
			remove(key)
			apply()
		}
	}
}
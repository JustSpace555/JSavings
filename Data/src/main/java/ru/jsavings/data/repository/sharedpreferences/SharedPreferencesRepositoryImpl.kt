package ru.jsavings.data.repository.sharedpreferences

import android.content.SharedPreferences
import android.util.Log
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
			//TODO Убрать
			Log.d(SharedPreferencesRepository::class.java.simpleName, "Put to shared preferences: $key = $value")
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
		}.also {
			//TODO Убрать
			Log.d(SharedPreferencesRepository::class.java.simpleName, "Get from shared preferences: $key = $it")
		}

	override fun removeValue(key: String) {
		with(sharedPreferences.edit()) {
			remove(key)
			//TODO Убрать
			Log.d(SharedPreferencesRepository::class.java.simpleName, "Remove from shared preferences: $key")
			apply()
		}
	}
}
package ru.jsavings.data.repository.sharedpreferences

import ru.jsavings.data.repository.common.BaseRepository
import kotlin.reflect.KClass

interface SharedPreferencesRepository : BaseRepository {

	object CacheConsts {
		internal const val FILE_NAME = "ru.jsavings.global_preferences"

		const val JS_CURRENT_ACCOUNT = "JS_CURRENT_ACCOUNT"
	}

	fun <T: Any> putValue(key: String, value: T)

	fun <T: Any> getValue(key: String, defaultValue: T): T

	fun removeValue(key: String)
}
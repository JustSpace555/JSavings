package ru.jsavings.data.repository.sharedpreferences

sealed class SharedPreferencesConsts {

	object JsGlobalSP : SharedPreferencesConsts() {
		internal const val FILE_NAME = "ru.jsavings.global_preferences"

		const val JS_CURRENT_ACCOUNT = "JS_CURRENT_ACCOUNT"
	}

	object NewAccountSP : SharedPreferencesConsts() {
		internal const val FILE_NAME = "ru.jsavings.new_account_preferences"

		const val JS_NEW_ACCOUNT_NAME = "JS_NEW_ACCOUNT_NAME"
		const val JS_NEW_ACCOUNT_CURRENCY = "JS_NEW_ACCOUNT_CURRENCY"
		const val JS_NEW_ACCOUNT_STARTING_BALANCE = "JS_NEW_ACCOUNT_STARTING_BALANCE"
	}
}
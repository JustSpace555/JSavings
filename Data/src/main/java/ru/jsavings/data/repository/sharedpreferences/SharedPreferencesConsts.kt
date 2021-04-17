package ru.jsavings.data.repository.sharedpreferences

//Global preferences
object JsSharedPreferences {
	internal const val FILE_NAME = "ru.jsavings.global_preferences"

	const val JS_CURRENT_ACCOUNT = "JS_CURRENT_ACCOUNT"
}

//New account
object NewAccountSharedPreferences {
	internal const val FILE_NAME = "ru.jsavings.new_account_preferences"

	const val JS_NEW_ACCOUNT_NAME = "JS_NEW_ACCOUNT_NAME"
	const val JS_NEW_ACCOUNT_CURRENCY = "JS_NEW_ACCOUNT_CURRENCY"
	const val JS_NEW_ACCOUNT_STARTING_BALANCE = "JS_NEW_ACCOUNT_STARTING_BALANCE"
}
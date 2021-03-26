package ru.jsavings.presentation.extensions

import androidx.lifecycle.MutableLiveData

fun <T: Any?> MutableLiveData<T>.default(initialValue: T) = apply { value = initialValue }
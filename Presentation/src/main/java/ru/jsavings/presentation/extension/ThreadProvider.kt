package ru.jsavings.presentation.extension

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

data class ThreadProvider(
	val backgroundThread: Scheduler = Schedulers.io(),
	val uiThread: Scheduler = AndroidSchedulers.mainThread()
)
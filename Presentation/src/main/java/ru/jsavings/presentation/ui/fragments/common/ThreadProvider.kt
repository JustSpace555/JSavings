package ru.jsavings.presentation.ui.fragments.common

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

data class ThreadProvider(
	val backgroundThread: Scheduler = Schedulers.io(),
	val uiThread: Scheduler = AndroidSchedulers.mainThread()
)
package ru.jsavings.domain.usecase.common

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers

abstract class CompletableUseCase <in PARAMS> : BaseUseCase() {

	internal abstract fun buildCompletableUseCase(params: PARAMS): Completable

	fun execute(
		onComplete: () -> Unit,
		onError: (t: Throwable) -> Unit,
		onFinish: () -> Unit = {},
		params: PARAMS
	) {
		buildCompletableUseCase(params)
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.doAfterTerminate(onFinish)
			.subscribe(onComplete, onError)
			.also { disposeBag.add(it) }
	}
}
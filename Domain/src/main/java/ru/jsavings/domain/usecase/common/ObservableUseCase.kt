package ru.jsavings.domain.usecase.common

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

abstract class ObservableUseCase<T, in PARAMS> : BaseUseCase() {

	internal abstract fun buildObservableUseCase(params: PARAMS): Observable<T>

	fun execute(
		onNext: (t: T) -> Unit,
		onError: (t: Throwable) -> Unit,
		onComplete: () -> Unit = {},
		params: PARAMS
	) {
		buildObservableUseCase(params)
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(onNext, onError, onComplete)
			.also { disposeBag.add(it) }
	}
}
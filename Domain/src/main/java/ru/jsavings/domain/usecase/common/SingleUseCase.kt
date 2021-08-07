package ru.jsavings.domain.usecase.common

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

abstract class SingleUseCase <T, in PARAMS> : BaseUseCase() {

	internal abstract fun buildSingleUseCase(params: PARAMS): Single<T>

	fun execute(
		onSuccess: (t: T) -> Unit,
		onError: (t: Throwable) -> Unit,
		onFinish: () -> Unit = {},
		params: PARAMS
	) {
		buildSingleUseCase(params)
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.doAfterTerminate(onFinish)
			.subscribe(onSuccess, onError)
			.also { disposeBag.add(it) }
	}
}
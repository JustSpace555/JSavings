package ru.jsavings.domain.usecase.common

import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseUseCase {

	protected val disposeBag = CompositeDisposable()

	fun dispose() = disposeBag.dispose()
}
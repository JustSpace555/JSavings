package ru.jsavings.domain.usecase.common

import io.reactivex.rxjava3.disposables.CompositeDisposable
import ru.jsavings.data.repository.common.BaseRepository

abstract class BaseUseCase {

	//TODO Подумать как сделать protected. Из-за CacheUseCase
	abstract val repository: BaseRepository

	protected val disposeBag = CompositeDisposable()

	fun dispose() = disposeBag.dispose()
}
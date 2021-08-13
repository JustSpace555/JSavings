package ru.jsavings.presentation.ui.fragments.common

import androidx.lifecycle.ViewModel
<<<<<<< refs/remotes/origin/dev
import ru.jsavings.data.repository.cache.CacheKeys
import ru.jsavings.domain.usecase.cache.CacheUseCase
=======
>>>>>>> Rework started
import ru.jsavings.domain.usecase.common.BaseUseCase

abstract class BaseViewModel (vararg val useCases: BaseUseCase) : ViewModel() {

	fun <T: Any> putToCache(key: CacheKeys, value: T) =
		useCases.filterIsInstance<CacheUseCase>().first().put(key, value)

	inline fun <reified T: Any> getFromCache(key: CacheKeys, defaultValue: T) =
		useCases.filterIsInstance<CacheUseCase>().first().get(key, defaultValue)

	fun removeFromCache(key: CacheKeys) =
		useCases.filterIsInstance<CacheUseCase>().first().remove(key)

	sealed class RequestState {
		object SendingState : RequestState()
		class ErrorState<T : Throwable> (val t: T): RequestState()
		class SuccessState<T> (val data: T): RequestState()
	}

	override fun onCleared() {
		super.onCleared()
		useCases.onEach { it.dispose() }
	}
}
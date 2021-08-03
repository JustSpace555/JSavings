package ru.jsavings.presentation.ui.fragments.common

import androidx.lifecycle.ViewModel
import ru.jsavings.data.repository.cache.CacheKeys
import ru.jsavings.domain.usecase.cache.CacheUseCase
import ru.jsavings.domain.usecase.common.BaseUseCase

abstract class BaseViewModel (vararg val useCases: BaseUseCase) : ViewModel() {

	fun <T: Any> putToCache(key: CacheKeys, value: T) =
		useCases.filterIsInstance<CacheUseCase>().first().put(key, value)

	inline fun <reified T: Any> getFromCache(key: CacheKeys, defaultValue: T) =
		useCases.filterIsInstance<CacheUseCase>().first().get(key, defaultValue)

	fun removeFromCache(key: CacheKeys) =
		useCases.filterIsInstance<CacheUseCase>().first().remove(key)

	sealed class NetworkRequestState <T> {
		class DefaultState<T> : NetworkRequestState<T>()
		class SendingState<T>: NetworkRequestState<T>()
		class ErrorState<T> (val t: Throwable): NetworkRequestState<T>()
		class OnSuccessState<T> (val data: T): NetworkRequestState<T>()
	}

	override fun onCleared() {
		super.onCleared()
		useCases.onEach { it.dispose() }
	}
}
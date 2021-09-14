package ru.jsavings.presentation.viewmodels.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import ru.jsavings.presentation.extension.ThreadProvider

/**
 * Base class of all ViewModels in JSavings app
 *
 * @author JustSpace
 */
abstract class BaseViewModel(
	private val disposeBag: CompositeDisposable,
	private val threadProvider: ThreadProvider
) : ViewModel() {

	/**
	 * Class of request's state to api or database
	 *
	 * @author JustSpace
	 */
	sealed class RequestState {

		/**
		 * Sending state
		 *
		 * @author JustSpace
		 */
		object SendingState : RequestState()

		/**
		 * Error state
		 * @param t [Throwable] instance of error
		 *
		 * @author JustSpace
		 */
		class ErrorState<T : Throwable> (val t: T): RequestState()

		/**
		 * Success state
		 * @param data Request answer data
		 *
		 * @author JustSpace
		 */
		class SuccessState<T> (val data: T): RequestState()
	}

	/**
	 * Execute usecase with return type [Single]
	 * @param liveData [LiveData] where to post state
	 * @param doOnSuccess Some lambda that will invoke only if request's response was successful
	 *
	 * @author JustSpace
	 */
	protected fun <T : Any> Single<T>.executeUseCase(
		liveData: MutableLiveData<RequestState>,
		doOnSuccess: (T) -> Unit = {}
	) {
		liveData.postValue(RequestState.SendingState)
		this.subscribeOn(threadProvider.backgroundThread)
			.observeOn(threadProvider.uiThread)
			.doOnSuccess { doOnSuccess(it) }
			.subscribe(
				{ successElement -> liveData.postValue(RequestState.SuccessState(successElement)) },
				{ t -> liveData.postValue(RequestState.ErrorState(t)) }
			)
			.also { disposeBag.add(it) }
	}

	/**
	 * Execute usecase with return type [Completable]
	 * @param liveData [LiveData] where to post state
	 *
	 * @author JustSpace
	 */
	protected fun Completable.executeUseCase(
		liveData: MutableLiveData<RequestState>,
		doOnSuccess: () -> Unit = {}
	) {
		liveData.postValue(RequestState.SendingState)
		this.subscribeOn(threadProvider.backgroundThread)
			.observeOn(threadProvider.uiThread)
			.doOnComplete(doOnSuccess)
			.subscribe(
				{ liveData.postValue(RequestState.SuccessState(Unit)) },
				{ t -> liveData.postValue(RequestState.ErrorState(t)) }
			)
			.also { disposeBag.add(it) }
	}

	protected fun <T : Any> Flowable<T>.executeUseCase(
		liveData: MutableLiveData<RequestState>,
		doOnNext: (T) -> Unit = {}
	) {
		liveData.postValue(RequestState.SendingState)
		this.subscribeOn(threadProvider.backgroundThread)
			.observeOn(threadProvider.uiThread)
			.doOnNext(doOnNext)
			.subscribe(
				{ successNextElement -> liveData.postValue(RequestState.SuccessState(successNextElement)) },
				{ t -> liveData.postValue(RequestState.ErrorState(t)) }
			)
			.also { disposeBag.add(it) }
		//TODO Подумать над BackPressure
	}

	override fun onCleared() {
		super.onCleared()
		disposeBag.dispose()
	}
}
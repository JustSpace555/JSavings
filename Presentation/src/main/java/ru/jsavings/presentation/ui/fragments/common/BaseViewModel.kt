package ru.jsavings.presentation.ui.fragments.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

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
	 *
	 * @author JustSpace
	 */
	protected fun <T> Single<T>.executeUseCase(liveData: MutableLiveData<RequestState>) {
		liveData.postValue(RequestState.SendingState)
		this.subscribeOn(threadProvider.backgroundThread)
			.observeOn(threadProvider.uiThread)
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
	protected fun Completable.executeUseCase(liveData: MutableLiveData<RequestState>) {
		liveData.postValue(RequestState.SendingState)
		this.subscribeOn(threadProvider.backgroundThread)
			.observeOn(threadProvider.uiThread)
			.subscribe(
				{ liveData.postValue(RequestState.SuccessState(Unit)) },
				{ t -> liveData.postValue(RequestState.ErrorState(t)) }
			)
			.also { disposeBag.add(it) }
	}

	override fun onCleared() {
		super.onCleared()
		disposeBag.dispose()
	}
}
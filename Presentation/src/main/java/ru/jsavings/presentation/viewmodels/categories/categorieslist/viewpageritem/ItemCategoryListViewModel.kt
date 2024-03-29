package ru.jsavings.presentation.viewmodels.categories.categorieslist.viewpageritem

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.disposables.CompositeDisposable
import ru.jsavings.domain.model.database.category.TransactionCategoryType
import ru.jsavings.domain.usecase.database.category.DeleteTransactionCategoryByIdUseCase
import ru.jsavings.presentation.extension.ThreadProvider
import ru.jsavings.presentation.viewmodels.common.BaseViewModel

/**
 * View model for ItemCategoryListFragment
 * @param deleteTransactionCategoryByIdUseCase [DeleteTransactionCategoryByIdUseCase] to interact with database
 * @param compositeDisposable [CompositeDisposable]
 * @param threadProvider [ThreadProvider]
 *
 * @author JustSpace
 */
class ItemCategoryListViewModel(
	private val deleteTransactionCategoryByIdUseCase: DeleteTransactionCategoryByIdUseCase,
	private val compositeDisposable: CompositeDisposable = CompositeDisposable(),
	private val threadProvider: ThreadProvider = ThreadProvider()
) : BaseViewModel(compositeDisposable, threadProvider) {

	lateinit var transactionCategoryType: TransactionCategoryType

	private val _requestDeleteCategoryState = MutableLiveData<RequestState>()
	/**
	 * Livedata for deleting transaction category request state
	 *
	 * @author JustSpace
	 */
	val requestDeleteCategoryState = _requestDeleteCategoryState as LiveData<RequestState>

	/**
	 * Request delete transaction category from database
	 * @param categoryId Id of category that must be deleted
	 *
	 * @author JustSpace
	 */
	fun requestDeleteCategory(categoryId: Long) {
		_requestDeleteCategoryState.postValue(RequestState.SendingState)
		deleteTransactionCategoryByIdUseCase(categoryId)
			.subscribeOn(threadProvider.backgroundThread)
			.observeOn(threadProvider.uiThread)
			.subscribe(
				{ _requestDeleteCategoryState.postValue(RequestState.SuccessState(categoryId)) },
				{ t -> _requestDeleteCategoryState.postValue(RequestState.ErrorState(t)) }
			)
			.also { compositeDisposable.add(it) }
	}
}
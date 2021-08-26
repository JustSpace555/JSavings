package ru.jsavings.presentation.ui.fragments.categories.categorieslist

import androidx.annotation.ColorInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.disposables.CompositeDisposable
import ru.jsavings.domain.interactor.database.TransactionCategoryInteractor
import ru.jsavings.domain.model.database.transaction.category.TransactionCategory
import ru.jsavings.domain.model.database.transaction.category.TransactionCategoryType
import ru.jsavings.domain.usecase.cache.CacheKeys
import ru.jsavings.domain.usecase.cache.CacheUseCase
import ru.jsavings.presentation.ui.fragments.common.BaseViewModel
import ru.jsavings.presentation.ui.fragments.common.ThreadProvider
import java.lang.IllegalArgumentException

/**
 * ViewModel for CategoriesListFragment
 * @param transactionCategoryInteractor To interact with database
 * @param cacheUseCase To get current account id from cache
 * @param compositeDisposable To dispose all usecases
 * @param threadProvider Provides threads for usecases
 *
 * @author Михаил Мошков
 */
class CategoriesListViewModel(
	private val transactionCategoryInteractor: TransactionCategoryInteractor,
	private val cacheUseCase: CacheUseCase,
	compositeDisposable: CompositeDisposable = CompositeDisposable(),
	threadProvider: ThreadProvider = ThreadProvider()
) : BaseViewModel(compositeDisposable, threadProvider) {

	var name = ""
	var type = ""

	@ColorInt
	var color = 0

	val categoriesList = mutableListOf<TransactionCategory>()

	private val _requestAllCategoriesState = MutableLiveData<RequestState>()
	/**
	 * LiveData state for getting transaction categories request
	 *
	 * @author Михаил Мошков
	 */
	val requestAllTransactionsState = _requestAllCategoriesState as LiveData<RequestState>

	/**
	 * Request transaction categories from database
	 *
	 * @author Михаил Мошков
	 */
	fun requestAllCategories() {
		val accountId = cacheUseCase.get(CacheKeys.JS_CURRENT_ACCOUNT, -1L)

		if (accountId == -1L) {
			_requestAllCategoriesState.postValue(
				RequestState.ErrorState(IllegalArgumentException("Current account id = -1"))
			)
			return
		}

		transactionCategoryInteractor
			.getAllCategoriesByAccountIdUseCase(accountId)
			.executeUseCase(_requestAllCategoriesState)
	}
}
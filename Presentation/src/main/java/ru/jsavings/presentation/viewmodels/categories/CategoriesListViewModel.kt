package ru.jsavings.presentation.viewmodels.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.disposables.CompositeDisposable
import ru.jsavings.domain.usecase.database.category.GetAllCategoriesByAccountIdUseCase
import ru.jsavings.presentation.extension.ThreadProvider
import ru.jsavings.presentation.viewmodels.common.BaseViewModel

/**
 * ViewModel for CategoriesListFragment
 * @param requestAllCategoriesByAccountIdUseCase [GetAllCategoriesByAccountIdUseCase] to get all transaction categories
 * @param cacheUseCase To get current account id from cache
 * @param compositeDisposable To dispose all usecases
 * @param threadProvider Provides threads for usecases
 *
 * @author Михаил Мошков
 */
class CategoriesListViewModel(
	private val requestAllCategoriesByAccountIdUseCase: GetAllCategoriesByAccountIdUseCase
) : BaseViewModel(CompositeDisposable(), ThreadProvider()) {

	private val _requestAllCategoriesState = MutableLiveData<RequestState>()
	/**
	 * LiveData state for getting transaction categories request state
	 *
	 * @author Михаил Мошков
	 */
	val requestAllCategoriesState = _requestAllCategoriesState as LiveData<RequestState>

	/**
	 * Request transaction categories from database
	 *
	 * @author Михаил Мошков
	 */
	fun requestAllCategories(accountId: Long) = requestAllCategoriesByAccountIdUseCase(accountId)
		.executeUseCase(_requestAllCategoriesState)
}
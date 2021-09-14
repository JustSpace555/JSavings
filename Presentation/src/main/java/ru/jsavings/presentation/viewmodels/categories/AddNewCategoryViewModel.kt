package ru.jsavings.presentation.viewmodels.categories

import androidx.annotation.ColorInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.disposables.CompositeDisposable
import ru.jsavings.domain.interactor.database.TransactionCategoryInteractor
import ru.jsavings.domain.model.database.category.TransactionCategory
import ru.jsavings.domain.model.database.category.TransactionCategoryType
import ru.jsavings.domain.usecase.cache.CacheKeys
import ru.jsavings.domain.usecase.cache.CacheUseCase
import ru.jsavings.presentation.extension.ThreadProvider
import ru.jsavings.presentation.viewmodels.common.BaseViewModel

/**
 * ViewModel for AddNewCategoryFragment
 * @param transactionCategoryInteractor [TransactionCategoryInteractor] to interact with database
 * @param compositeDisposable [CompositeDisposable] to dispose all usecases
 * @param threadProvider [ThreadProvider] to get threads to usecases
 *
 * @author Михаил Мошков
 */
class AddNewCategoryViewModel(
	private val transactionCategoryInteractor: TransactionCategoryInteractor
) : BaseViewModel(CompositeDisposable(), ThreadProvider()) {

	companion object {
		const val DATA_VALID = 0
		const val NAME_EMPTY = 1
		const val NAME_TAKEN = 1 shl 1
	}

	var name = ""
	lateinit var type: TransactionCategoryType

	@ColorInt
	var color = 0

	private val _requestAllCategoriesState = MutableLiveData<RequestState>()
	/**
	 * Livedata for getting all available transaction categories state request
	 *
	 * @author Михаил Мошков
	 */
	val requestAllCategoriesState = _requestAllCategoriesState as LiveData<RequestState>

	/**
	 * Request transaction categories from database by acctount id
	 * @param accountId Id of account
	 *
	 * @author JustSpace
	 */
	fun requestAllCategories(accountId: Long) = transactionCategoryInteractor
		.getAllCategoriesByAccountIdUseCase(accountId)
		.executeUseCase(_requestAllCategoriesState)

	private val _requestSaveCategoryState = MutableLiveData<RequestState>()
	/**
	 * Livedata for saving new transaction category state request
	 *
	 * @author Михаил Мошков
	 */
	val requestSaveCategoryState = _requestSaveCategoryState as LiveData<RequestState>

	/**
	 * Request to save new category
	 *
	 * @author Михаил Мошков
	 */
	fun requestSaveCategory(accountId: Long) {
		val newCategory = TransactionCategory(
			accountId = accountId,
			categoryType = type,
			color = color,
			name = name,
			iconPath = "" //TODO
		)

		transactionCategoryInteractor.insertNewCategoryUseCase(newCategory).executeUseCase(_requestSaveCategoryState)
	}

	fun requestUpdateCategory(accountId: Long, categoryId: Long) {

		val newCategory = TransactionCategory(
			accountId = accountId,
			categoryType = type,
			color = color,
			name = name,
			iconPath = "", //TODO
			categoryId = categoryId
		)

		transactionCategoryInteractor
			.updateTransactionCategoryUseCase(newCategory)
			.executeUseCase(_requestSaveCategoryState)
	}

	/**
	 * Validating input data
	 * @param allCategories List of all categories
	 * @param categoryId Id of category to update. -1 if new category
	 * @return [DATA_VALID] or Int with validation errors flags
	 * @see NAME_EMPTY
	 * @see NAME_TAKEN
	 *
	 * @author Михаил Мошков
	 */
	fun validateInputData(allCategories: List<TransactionCategory>, categoryId: Long = -1L): Int {
		var result = DATA_VALID

		if (name.isEmpty() || name.isBlank()) result = NAME_EMPTY

		val checkCategoryNamesList = if (categoryId != -1L) {
			allCategories.filter { it.categoryId != categoryId }
		} else {
			allCategories
		}.map { it.name }

		if (checkCategoryNamesList.contains(name)) result = NAME_TAKEN

		return result
	}
}
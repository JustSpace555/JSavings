package ru.jsavings.presentation.ui.fragments.categories.newcategory

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
 * ViewModel for AddNewCategoryFragment
 * @param transactionCategoryInteractor [TransactionCategoryInteractor] to interact with database
 * @param cacheUseCase [CacheUseCase] to get current account id
 * @param compositeDisposable [CompositeDisposable] to dispose all usecases
 * @param threadProvider [ThreadProvider] to get threads to usecases
 *
 * @author Михаил Мошков
 */
class AddNewCategoryViewModel(
	private val transactionCategoryInteractor: TransactionCategoryInteractor,
	private val cacheUseCase: CacheUseCase,
	compositeDisposable: CompositeDisposable = CompositeDisposable(),
	threadProvider: ThreadProvider = ThreadProvider()
) : BaseViewModel(compositeDisposable, threadProvider) {

	companion object {
		const val DATA_VALID = 0
		const val NAME_EMPTY = 1
		const val NAME_TAKEN = 1 shl 1
		const val TYPE_EMPTY = 1 shl 2
	}

	val allCategories = mutableListOf<TransactionCategory>()

	var name = ""
	var type = ""

	@ColorInt
	var color = 0

	private val _requestAllCategoriesState = MutableLiveData<RequestState>()
	/**
	 * Livedata for getting all available transaction categories state request
	 *
	 * @author Михаил Мошков
	 */
	val requestAllCategoriesState = _requestAllCategoriesState as LiveData<RequestState>

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
	fun requestSaveCategory() {
		if (validateInputData() != DATA_VALID) return

		val accountId = cacheUseCase.get(CacheKeys.JS_CURRENT_ACCOUNT, -1L)
		if (accountId == -1L) {
			_requestSaveCategoryState.postValue(
				RequestState.ErrorState(IllegalArgumentException("Current account id = -1"))
			)
			return
		}

		val newCategory = TransactionCategory(
			accountId = accountId,
			categoryType = TransactionCategoryType.valueOf(type),
			color = color,
			name = name,
			iconPath = "" //TODO
		)

		transactionCategoryInteractor.insertNewCategoryUseCase(newCategory).executeUseCase(_requestSaveCategoryState)
	}

	/**
	 * Validating input data
	 * @return [DATA_VALID] or Int with validation errors flags
	 * @see NAME_EMPTY
	 * @see NAME_TAKEN
	 * @see TYPE_EMPTY
	 *
	 * @author Михаил Мошков
	 */
	fun validateInputData(): Int {
		var validationResult = DATA_VALID

		validationResult = when {
			name.isEmpty() || name.isBlank() -> validationResult or NAME_EMPTY
			allCategories.map { it.name }.contains(name) -> validationResult or NAME_TAKEN
			else -> DATA_VALID
		}

		if (type.isEmpty() || type.isBlank())
			validationResult = validationResult or TYPE_EMPTY

		return validationResult
	}

	/**
	 * Check if certain check is valid
	 * @param validationResult Result of all validation
	 * @param validationCode Code of instance to check
	 * @return True if validation is INVALID, false otherwise
	 * @see validateInputData
	 *
	 * @author JustSpace
	 */
	fun getValidationResult(validationResult: Int, validationCode: Int) =
		validationResult and validationCode == DATA_VALID
}
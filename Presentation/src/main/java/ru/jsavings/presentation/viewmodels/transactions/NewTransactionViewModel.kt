package ru.jsavings.presentation.viewmodels.transactions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.disposables.CompositeDisposable
import ru.jsavings.domain.interactor.database.DataBaseInteractor
import ru.jsavings.domain.model.database.transaction.Transaction
import ru.jsavings.domain.model.database.category.TransactionCategory
import ru.jsavings.domain.model.database.category.TransactionCategoryType
import ru.jsavings.domain.model.database.wallet.Wallet
import ru.jsavings.presentation.extension.ThreadProvider
import ru.jsavings.presentation.viewmodels.common.BaseViewModel
import java.lang.NullPointerException
import java.util.*

/**
 * ViewModel for NewTransactionFragment
 * @param dataBaseInteractor Interactor for getting wallets and save transactions
 *
 * @author Михаил Мошков
 */
class NewTransactionViewModel(
	private val dataBaseInteractor: DataBaseInteractor
) : BaseViewModel(CompositeDisposable(), ThreadProvider()) {

	companion object {
		const val DATA_VALID = 0
		const val TIME_INVALID = 1
		const val CATEGORY_EMPTY = 1 shl 1
		const val FROM_WALLET_EMPTY = 1 shl 2
		const val TO_WALLET_EMPTY = 1 shl 4
		const val TRANSACTION_SUM_EMPTY = 1 shl 4
		const val TRANSACTION_SUM_INVALID = 1 shl 5
		const val TRANSACTION_SUM_HIGHER = 1 shl 6
	}

	var transactionDate = -1L
	var transactionTime = -1L
	var transactionType = TransactionCategoryType.INCOME
	var fromWallet: Wallet? = null
	var toWallet: Wallet? = null
	var transactionSum = ""
	var transactionDescription = ""
	var transactionCategory: TransactionCategory? = null

	private val _requestAllWalletsState = MutableLiveData<RequestState>()
	/**
	 * LiveData of getting all wallets request
	 *
	 * @author Михаил Мошков
	 */
	val requestAllWalletsState: LiveData<RequestState> = _requestAllWalletsState

	/**
	 * Request all wallets from database by account id
	 * @param accountId Id of account
	 *
	 * @author Михаил Мошков
	 */
	fun requestAllWallets(accountId: Long) = dataBaseInteractor.walletInteractor
		.getWalletsByAccountIdUseCase(accountId)
		.executeUseCase(_requestAllWalletsState)

	private val _requestTransactionCategoryState = MutableLiveData<RequestState>()
	/**
	 * Livedata of getting transaction category request state
	 *
	 * @author JustSpace
	 */
	val requestTransactionCategoryState: LiveData<RequestState> = _requestTransactionCategoryState

	/**
	 * Request transaction category by id
	 * @param categoryId Id of category to request
	 *
	 * @author JustSpace
	 */
	fun requestTransactionCategory(categoryId: Long) {
		if (categoryId == -1L) return

		dataBaseInteractor.categoriesInteractor
			.getTransactionCategoryByIdUseCase(categoryId)
			.executeUseCase(_requestTransactionCategoryState) { transactionCategory = it }
	}

	private val _requestSaveTransactionState = MutableLiveData<RequestState>()
	/**
	 * LiveData of saving new transaction request state
	 *
	 * @author JustSpace
	 */
	val requestSaveTransactionSate = _requestSaveTransactionState as LiveData<RequestState>

	/**
	 * Request save new transaction in database
	 *
	 * @author JustSpace
	 */
	fun requestSaveTransaction(accountId: Long) {

		val transaction = Transaction(
			accountId = accountId,
			sumInWalletCurrency = transactionSum.toDouble(),
			description = transactionDescription,
			describePicturePath = "", //TODO
			sumInAccountCurrency = 0.0,
			transferSum = 0.0,
			fromWallet = fromWallet,
			toWallet = toWallet,
			category = transactionCategory,
			dateTime = Date(transactionTime),
			dateDay = Date(transactionDate)
		)

		dataBaseInteractor.transactionInteractor
			.saveNewTransactionUseCase(transaction)
			.executeUseCase(_requestSaveTransactionState)
	}

	/**
	 * Checks if all data inputted by user is valid
	 * @return Result of check
	 * @see DATA_VALID
	 * @see TIME_INVALID
	 * @see CATEGORY_EMPTY
	 * @see FROM_WALLET_EMPTY
	 * @see TO_WALLET_EMPTY
	 * @see TRANSACTION_SUM_EMPTY
	 * @see TRANSACTION_SUM_INVALID
	 * @see TRANSACTION_SUM_HIGHER
	 *
	 * @author JustSpace
	 */
	fun validateInputData(): Int {
		var currentResult = DATA_VALID

		fun combineTransactionDateAndTime(): Long {
			val timeCal = Calendar.getInstance().apply { time = Date(transactionTime) }
			val dateCal = Calendar.getInstance().apply {
				time = Date(transactionDate)
				set(Calendar.HOUR_OF_DAY, timeCal[Calendar.HOUR_OF_DAY])
				set(Calendar.MINUTE, timeCal[Calendar.MINUTE])
				set(Calendar.SECOND, timeCal[Calendar.SECOND])
				set(Calendar.MILLISECOND, timeCal[Calendar.MILLISECOND])
			}
			return dateCal.time.time
		}

		if (combineTransactionDateAndTime() > System.currentTimeMillis())
			currentResult = currentResult or TIME_INVALID
		when(transactionType) {
			TransactionCategoryType.INCOME -> {
				if (toWallet == null) currentResult = currentResult or TO_WALLET_EMPTY
				if (transactionCategory == null) currentResult = currentResult or CATEGORY_EMPTY
			}
			TransactionCategoryType.CONSUMPTION -> {
				if (fromWallet == null) currentResult = currentResult or FROM_WALLET_EMPTY
				if (transactionCategory == null) currentResult = currentResult or CATEGORY_EMPTY
			}
			TransactionCategoryType.TRANSFER -> {
				if (fromWallet == null) currentResult = currentResult or FROM_WALLET_EMPTY
				if (toWallet == null) currentResult = currentResult or TO_WALLET_EMPTY
			}
		}
		if (transactionSum.isEmpty() || transactionSum.isBlank())
			currentResult = currentResult or TRANSACTION_SUM_EMPTY
		else try {
			val sum = transactionSum.toDouble()

			if ((transactionType == TransactionCategoryType.CONSUMPTION ||
				transactionType == TransactionCategoryType.TRANSFER) && sum > fromWallet!!.balance
			) {
				currentResult = currentResult or TRANSACTION_SUM_HIGHER
			}
		} catch (e: NumberFormatException) {
			currentResult = currentResult or TRANSACTION_SUM_INVALID
		} catch (e: NullPointerException) {
			return currentResult
		}

		return currentResult
	}

	/**
	 * Get result of check
	 * @param check Pair of validation result and validation code
	 * @return True if validation is INVALID.
	 * @see validateInputData
	 * @see DATA_VALID
	 * @see TIME_INVALID
	 * @see CATEGORY_EMPTY
	 * @see FROM_WALLET_EMPTY
	 * @see TO_WALLET_EMPTY
	 * @see TRANSACTION_SUM_EMPTY
	 * @see TRANSACTION_SUM_INVALID
	 * @see TRANSACTION_SUM_HIGHER
	 *
	 * @author JustSpace
	 */
	fun getValidationResult(check: Pair<Int, Int>): Boolean = check.first and check.second == check.second
}
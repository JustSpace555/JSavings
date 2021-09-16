package ru.jsavings.presentation.viewmodels.transactions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.disposables.CompositeDisposable
import ru.jsavings.domain.interactor.database.TransactionInteractor
import ru.jsavings.domain.usecase.database.account.GetAccountByIdUseCase
import ru.jsavings.presentation.extension.ThreadProvider
import ru.jsavings.presentation.viewmodels.common.BaseViewModel

/**
 * ViewModel for [ru.jsavings.presentation.ui.fragments.transactions.TransactionInfoFragment]
 * @param transactionInteractor [TransactionInteractor]
 * @param getAccountByIdUseCase [GetAccountByIdUseCase]
 *
 * @author JustSpace
 */
class TransactionInfoViewModel(
	private val transactionInteractor: TransactionInteractor,
	private val getAccountByIdUseCase: GetAccountByIdUseCase
) : BaseViewModel(CompositeDisposable(), ThreadProvider()) {

	private val _requestTransactionByIdState = MutableLiveData<RequestState>()
	/**
	 * Livedata for get transaction by id from database request state
	 *
	 * @author JustSpace
	 */
	val requestTransactionByIdState: LiveData<RequestState> = _requestTransactionByIdState

	/**
	 * Request transaction by id
	 * @param transactionId Id of transaction to request
	 * @see requestTransactionByIdState
	 *
	 * @author JustSpace
	 */
	fun requestTransactionById(transactionId: Long) = transactionInteractor.getTransactionByIdUseCase(transactionId)
		.executeUseCase(_requestTransactionByIdState)

	private val _requestDeleteTransactionState = MutableLiveData<RequestState>()

	/**
	 * Livedata for delete transaction by id from database request state
	 *
	 * @author JustSpace
	 */
	val requestDeleteTransactionState: LiveData<RequestState> = _requestDeleteTransactionState

	/**
	 * Request delete transaction by id from database
	 * @param transactionId Id of transaction to delete
	 * @see requestDeleteTransactionState
	 *
	 * @author JustSpace
	 */
	fun requestDeleteTransactionById(transactionId: Long) = transactionInteractor
		.deleteTransactionByIdUseCase(transactionId)
		.executeUseCase(_requestDeleteTransactionState)
}
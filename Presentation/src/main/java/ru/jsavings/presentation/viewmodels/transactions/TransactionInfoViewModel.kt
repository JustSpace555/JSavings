package ru.jsavings.presentation.viewmodels.transactions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.disposables.CompositeDisposable
import ru.jsavings.domain.usecase.database.transaction.GetTransactionByIdUseCase
import ru.jsavings.presentation.extension.ThreadProvider
import ru.jsavings.presentation.viewmodels.common.BaseViewModel

class TransactionInfoViewModel(
	private val getTransactionByIdUseCase: GetTransactionByIdUseCase
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
	fun requestTransactionById(transactionId: Long) = getTransactionByIdUseCase(transactionId)
		.executeUseCase(_requestTransactionByIdState)
}
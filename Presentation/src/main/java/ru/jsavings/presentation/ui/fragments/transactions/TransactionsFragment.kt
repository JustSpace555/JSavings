package ru.jsavings.presentation.ui.fragments.transactions

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import ru.jsavings.R
import ru.jsavings.data.repository.cache.CacheKeys
import ru.jsavings.data.repository.cache.CacheRepository
import ru.jsavings.databinding.MainFragmentTransactionsBinding
import ru.jsavings.presentation.ui.fragments.common.BaseFragment

class TransactionsFragment : BaseFragment() {

    override val viewModel by viewModel<TransactionsViewModel>()

    override val bindingUtil by lazy { MainFragmentTransactionsBinding.inflate(layoutInflater) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.main_fragment_transactions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cache by inject<CacheRepository>()
        Log.d("TransactionFragment", "Account id: ${cache.getValue(CacheKeys.JS_CURRENT_ACCOUNT, -1L)}")
    }
}
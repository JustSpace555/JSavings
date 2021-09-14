package ru.jsavings.presentation.ui.fragments.transactions

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import org.koin.android.viewmodel.ext.android.viewModel
import ru.jsavings.R
import ru.jsavings.databinding.FragmentTransactionInfoBinding
import ru.jsavings.domain.model.database.category.TransactionCategoryType
import ru.jsavings.presentation.ui.fragments.common.BaseFragment
import ru.jsavings.presentation.viewmodels.transactions.TransactionInfoViewModel
import java.text.SimpleDateFormat
import kotlin.properties.Delegates

class TransactionInfoFragment : BaseFragment() {

	override lateinit var bindingUtil: FragmentTransactionInfoBinding
	override val viewModel by viewModel<TransactionInfoViewModel>()

	private var transactionId by Delegates.notNull<Long>()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val args by navArgs<TransactionInfoFragmentArgs>()
		transactionId = args.transactionId
	}
//
//	private fun setUpObserveGetTransactionById() = viewModel.requestTransactionByIdState.subscribe<TransactionGroup>(
//		hideLoading = true,
//		onSuccess = { bindTransaction(it) },
//		onError = { showTextSnackBar(
//			text = it.getErrorString(),
//			actionText = getString(R.string.retry),
//			action = { viewModel.requestTransactionById(transactionId) })
//		},
//		onSending = { showLoading(R.string.loading_getting_transaction) }
//	)
//
//	@SuppressLint("SetTextI18n")
//	private fun bindTransaction(transaction: TransactionGroup) {
//		with(bindingUtil) {
//			ivTransactionImage.setBackgroundColor(transaction.category.color)
//			//TODO иконка
//			textTransactionName.text = if (transaction.transaction.description.isEmpty())
//				transaction.category.name
//			else
//				transaction.transaction.description
//
//			textTransactionDateData.text = SimpleDateFormat("yyyy-MMMM-dd HH:mm:ss", locale)
//				.format(transaction.transaction.date)
//			textTransactionCategoryData.text = transaction.category.name
//
//			when(transaction.category.categoryType) {
//				TransactionCategoryType.INCOME -> {
//					textTransactionFromWallet.isVisible = false
//					textTransactionFromWalletData.isVisible = false
//					textTransactionToWalletData.text = "+" + transaction.transaction.sumInWalletCurrency
//				}
//			}
//		}
//	}
}
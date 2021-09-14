package ru.jsavings.presentation.ui.fragments.account.newaccount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.koin.android.viewmodel.ext.android.viewModel
import ru.jsavings.R
import ru.jsavings.databinding.FragmentNewAccountAddNameBinding
import ru.jsavings.domain.model.database.Account
import ru.jsavings.presentation.ui.fragments.common.BaseFragment
import ru.jsavings.presentation.viewmodels.common.BaseViewModel
import ru.jsavings.presentation.viewmodels.account.newaccount.AddNewAccountNameViewModel

class AddNewAccountNameFragment : BaseFragment() {

	override val viewModel by viewModel<AddNewAccountNameViewModel>()
	override lateinit var bindingUtil: FragmentNewAccountAddNameBinding

	private val allAccounts = mutableListOf<Account>()

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		bindingUtil = FragmentNewAccountAddNameBinding.inflate(inflater, container, false)
		return bindingUtil.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setUpObservers()
		viewModel.requestAllAccounts()
	}

	private fun setUpObservers() {
		viewModel.allAccountsRequestState.subscribe<List<Account>>(
			hideLoading = true,
			onSuccess = {
				allAccounts.addAll(it)
				setUpUi()
			},
			onError = { showTextSnackBar(
				text = it.getErrorString(),
				actionText = getString(R.string.retry),
				action = { viewModel.requestAllAccounts() }
			)},
			onSending = { showLoading() }
		)
	}

	private fun setUpUi() {
		with(bindingUtil) {
			if (viewModel.currentInputAccountName.isNotEmpty())
				tilNewAccountName.editText?.setText(viewModel.currentInputAccountName)

			tilNewAccountName.editText?.doOnTextChanged { text, _, _, _ ->
				tilNewAccountName.isErrorEnabled = false
				text?.let {
					viewModel.currentInputAccountName = it.toString()
					nextButton.isEnabled = it.toString().isNotEmpty() && it.toString().isNotBlank()
				}
			}

			nextButton.setOnClickListener {
				if (allAccounts.asSequence().map { it.name }.contains(viewModel.currentInputAccountName)) {
					tilNewAccountName.error = getString(R.string.error_new_account_name_is_exists)
					tilNewAccountName.isErrorEnabled = true
				} else {
					val args by navArgs<AddNewAccountNameFragmentArgs>()
					findNavController().navigate(
						AddNewAccountNameFragmentDirections.actionAddNewAccountNameToChooseCurrencyNewAccountFragment(
							args.isEducationNeeded, viewModel.currentInputAccountName
						)
					)
				}
			}

			hideKeyBoardOnRootTouchClick()
		}
	}
}
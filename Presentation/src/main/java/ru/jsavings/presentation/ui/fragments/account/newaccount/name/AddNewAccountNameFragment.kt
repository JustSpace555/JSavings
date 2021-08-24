package ru.jsavings.presentation.ui.fragments.account.newaccount.name

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.koin.android.viewmodel.ext.android.viewModel
import ru.jsavings.R
import ru.jsavings.databinding.NewAccountFragmentAddNameBinding
import ru.jsavings.domain.model.database.Account
import ru.jsavings.presentation.ui.fragments.common.BaseFragment
import ru.jsavings.presentation.ui.fragments.common.BaseViewModel

class AddNewAccountNameFragment : BaseFragment() {

	override val viewModel by viewModel<AddNewAccountNameViewModel>()
	override lateinit var bindingUtil: NewAccountFragmentAddNameBinding

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		bindingUtil = NewAccountFragmentAddNameBinding.inflate(inflater, container, false)
		return bindingUtil.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		viewModel.requestAllAccounts()

		with(bindingUtil) {
			if (viewModel.currentInputAccountName.isNotEmpty()) {
				tilNewAccountName.editText?.setText(viewModel.currentInputAccountName)
				viewModel.validateNewAccountName()
			}

			tilNewAccountName.editText?.doOnTextChanged { text, _, _, _ ->
				tilNewAccountName.isErrorEnabled = false
				nextButton.isEnabled = true
				text?.let {
					viewModel.currentInputAccountName = it.toString()
					viewModel.validateNewAccountName()
				}
			}

			nextButton.setOnClickListener {
				val args by navArgs<AddNewAccountNameFragmentArgs>()
				findNavController().navigate(
					AddNewAccountNameFragmentDirections.actionAddNewAccountNameToChooseCurrencyNewAccountFragment(
						args.isEducationNeeded, viewModel.currentInputAccountName
					)
				)
			}

			hideKeyBoardOnRootTouchClick(root)
		}
	}

	override fun onStart() {
		super.onStart()

		viewModel.allAccountsRequestState.observe(viewLifecycleOwner) { state ->
			when (state) {
				is BaseViewModel.RequestState.SuccessState<*> -> {
					hideLoading()

					@Suppress("UNCHECKED_CAST")
					viewModel.setAccounts(state.data as List<Account>)
				}
				is BaseViewModel.RequestState.ErrorState<*> -> {
					hideLoading()
					showTextSnackBar(state.t.getErrorString())
				}
				else -> showLoading()
			}
		}

		viewModel.currentInputAccountNameState.observe(viewLifecycleOwner) { state ->
			when(state) {
				is AddNewAccountNameViewModel.CurrentInputAccountNameState.EmptyState ->
					setTextInputLayoutErrorAndDisableButton(R.string.error_new_account_name_is_empty)
				is AddNewAccountNameViewModel.CurrentInputAccountNameState.TakenState ->
					setTextInputLayoutErrorAndDisableButton(R.string.error_new_account_name_is_exists)
				is AddNewAccountNameViewModel.CurrentInputAccountNameState.ValidState ->
					bindingUtil.nextButton.isEnabled = true
			}
		}
	}

	private fun setTextInputLayoutErrorAndDisableButton(stringId: Int) {
		with(bindingUtil) {
			tilNewAccountName.error = getString(stringId)
			tilNewAccountName.isErrorEnabled = true
			nextButton.isEnabled = false
		}
	}
}
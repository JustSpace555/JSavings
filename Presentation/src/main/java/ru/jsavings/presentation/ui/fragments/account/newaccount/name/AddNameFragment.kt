package ru.jsavings.presentation.ui.fragments.account.newaccount.name

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.koin.android.viewmodel.ext.android.viewModel
import ru.jsavings.R
<<<<<<< refs/remotes/origin/dev
import ru.jsavings.data.model.database.Account
=======
>>>>>>> Rework started
import ru.jsavings.databinding.NewAccountFragmentAddNameBinding
import ru.jsavings.presentation.ui.fragments.common.BaseFragment
import ru.jsavings.presentation.ui.fragments.common.BaseViewModel

class AddNewAccountName : BaseFragment() {

	override val viewModel by viewModel<AddNameViewModel>()
	override lateinit var bindingUtil: NewAccountFragmentAddNameBinding

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		bindingUtil = NewAccountFragmentAddNameBinding.inflate(inflater, container, false)
		return bindingUtil.root
	}

	@SuppressLint("ClickableViewAccessibility")
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		with(bindingUtil) {

			tilNewAccountName.editText?.doOnTextChanged { text, _, _, _ ->
				tilNewAccountName.isErrorEnabled = false
				text?.let {
					nextButton.isEnabled = it.isNotEmpty()
				}
			}

			nextButton.setOnClickListener { onNextButtonClickListener() }

			hideKeyBoardOnRootTouch(root)
		}
	}

	@Suppress("UNCHECKED_CAST")
	private fun onNextButtonClickListener() {
		viewModel.requestAllAccounts()

		viewModel.allAccountsRequestState.observe(viewLifecycleOwner) { state ->
			val newAccountName = bindingUtil.tietNewAccountName.text?.toString()
			if (newAccountName != null)
				when (state) {
					is BaseViewModel.RequestState.SuccessState<*> -> {
						val newAccountName = bindingUtil.tietNewAccountName.text?.toString()
						if (newAccountName != null) {
							validateNewAcctounNameAndMove(
								newAccountName, state.data as List<Account>
							)
						}
					}
					is BaseViewModel.RequestState.ErrorState<*> -> {
						showTextSnackBar(state.t.getErrorString())
					}
					else -> {}
				}
		}
	}

	private fun validateNewAcctounNameAndMove(
		newAccountName: String,
		accountsList: List<Account>
	) {
		if (newAccountName.isEmpty() || newAccountName.isBlank()) {
			bindingUtil.tilNewAccountName.apply {
				isErrorEnabled = true
				error = getString(R.string.error_new_account_name_is_empty)
			}
			return
		}

		val args by navArgs<AddNewAccountNameArgs>()
		if (accountsList.isEmpty()) {
			findNavController().navigate(
				AddNewAccountNameDirections
					.actionAddNewAccountNameToChooseCurrencyNewAccountFragment(
						isEducationNeeded = args.isEducationNeeded,
						newAccountName = newAccountName
					)
			)
			return
		}

		if (accountsList.map { account -> account.name }.contains(newAccountName)) {
			bindingUtil.tilNewAccountName.apply {
				isErrorEnabled = true
				error = getString(R.string.error_new_account_name_is_exists)
			}
			return
		}

		findNavController().navigate(
			AddNewAccountNameDirections.actionAddNewAccountNameToChooseCurrencyNewAccountFragment(
				isEducationNeeded = args.isEducationNeeded,
				newAccountName = newAccountName
			)
		)
	}
}
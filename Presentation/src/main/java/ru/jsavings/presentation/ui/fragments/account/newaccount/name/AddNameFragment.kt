package ru.jsavings.presentation.ui.fragments.account.newaccount.name

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.koin.android.viewmodel.ext.android.viewModel
import ru.jsavings.R
import ru.jsavings.data.model.Account
import ru.jsavings.data.repository.sharedpreferences.NewAccountSharedPreferences
import ru.jsavings.databinding.NewAccountFragmentAddNameBinding
import ru.jsavings.domain.usecase.sharedpreferences.NewAccountSharedPreferencesUseCase
import ru.jsavings.presentation.ui.fragments.common.BaseFragment
import ru.jsavings.presentation.ui.fragments.intro.IntroFragment

class AddNewAccountName : BaseFragment() {

	override val viewModel by viewModel<AddNameViewModel>()
	override lateinit var bindingUtil: NewAccountFragmentAddNameBinding

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		bindingUtil = NewAccountFragmentAddNameBinding.inflate(inflater, container, false)

		viewModel.newAccountNameState.observe(viewLifecycleOwner, ::onAccountNameStateChanged)

		return bindingUtil.root
	}

	@SuppressLint("ClickableViewAccessibility")
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		bindingUtil.tilNewAccountName.editText?.doOnTextChanged { text, _, _, _ ->
			bindingUtil.tilNewAccountName.isErrorEnabled = false
			viewModel.onTextChanged(text)
		}

		viewModel.getFromSharedPreferences(
			NewAccountSharedPreferences.JS_NEW_ACCOUNT_NAME, String::class, ""
		).also {
			if (it.isNotEmpty()) {
				bindingUtil.tilNewAccountName.editText?.setText(it)
				bindingUtil.nextButton.isEnabled = true
			}
		}

		bindingUtil.nextButton.setOnClickListener(::onNextButtonClickListener)

		bindingUtil.root.setOnTouchListener { v, _ ->
			(requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
				.hideSoftInputFromWindow(v.windowToken, 0)
			bindingUtil.tilNewAccountName.clearFocus()
			true
		}
	}



	private fun onAccountNameStateChanged(state: AddNameViewModel.AccountNameState) {
		bindingUtil.nextButton.isEnabled = when(state) {
			is AddNameViewModel.AccountNameState.OnEmptyState -> false
			is AddNameViewModel.AccountNameState.OnReadyState -> true
		}
	}

	private fun onNextButtonClickListener(view: View) {
		viewModel.requestAllAccounts {
			showTextSnackBar(view, it.localizedMessage ?: getString(R.string.something_went_wrong))
		}

		viewModel.allAccountsListener.observe(viewLifecycleOwner) { accounts ->
			viewModel.newAccountName.value?.let {
				when {
					it.isEmpty() || it.isBlank() -> {
						bindingUtil.tilNewAccountName.apply {
							isErrorEnabled = true
							error = getString(R.string.error_new_account_name_is_empty)
						}
					}
					it in accounts.map { account -> account.name } -> {
						bindingUtil.tilNewAccountName.apply {
							isErrorEnabled = true
							error = getString(R.string.error_new_account_name_is_exists)
						}
					}
					else -> {
						viewModel.putToSharedPreferences(NewAccountSharedPreferences.JS_NEW_ACCOUNT_NAME, it)
						findNavController().navigate(
							AddNewAccountNameDirections.actionAddNewAccountNameToChooseCurrencyNewAccountFragment(
								navArgs<AddNewAccountNameArgs>().value.isEducationNeeded
							)
						)
					}
				}
			}
		}
	}
}
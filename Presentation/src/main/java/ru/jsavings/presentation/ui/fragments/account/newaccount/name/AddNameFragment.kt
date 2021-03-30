package ru.jsavings.presentation.ui.fragments.account.newaccount.name

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import org.koin.android.viewmodel.ext.android.viewModel
import ru.jsavings.R
import ru.jsavings.databinding.NewAccountFragmentAddNameBinding
import ru.jsavings.presentation.ui.fragments.common.BaseFragment

class AddNewAccountName : BaseFragment() {

	override val viewModel by viewModel<AddNameViewModel>()

	override val bindingUtil by lazy { NewAccountFragmentAddNameBinding.inflate(layoutInflater) }

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.new_account_fragment_add_name, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		bindingUtil.tilNewAccountName.editText?.doOnTextChanged { text, _, _, _ ->
			viewModel.onTextChanged(text)
		}

		viewModel.newAccountNameState.observe(viewLifecycleOwner, ::onStateChanged)
	}

	private fun onStateChanged(state: AddNameViewModel.AccountNameState) {
		bindingUtil.nextButton.isEnabled = when(state) {
			is AddNameViewModel.AccountNameState.OnEmptyState -> false
			is AddNameViewModel.AccountNameState.OnReadyState -> true
		}
	}
}
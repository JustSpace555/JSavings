package ru.jsavings.presentation.ui.fragments.newpurse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import kotlinx.android.synthetic.main.fragment_add_name_new_account.*
import org.koin.android.viewmodel.ext.android.viewModel
import ru.jsavings.R
import ru.jsavings.presentation.ui.fragments.common.BaseFragment

class AddNewAccountName : BaseFragment() {

	override val viewModel: AddNewAccountNameViewModel by viewModel()

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.fragment_add_name_new_account, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		til_new_account_name.editText?.doOnTextChanged { text, _, _, _ ->
			viewModel.onTextChanged(text)
		}

		viewModel.newAccountNameState.observe(viewLifecycleOwner, ::onStateChanged)
	}

	fun onStateChanged(state: AccountNameState) {
		next_button.isEnabled = when(state) {
			is AccountNameState.OnEmptyState -> false
			is AccountNameState.OnReadyState -> true
		}
	}
}
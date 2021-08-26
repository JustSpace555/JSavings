package ru.jsavings.presentation.ui.fragments.categories.newcategory

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import org.koin.android.viewmodel.ext.android.viewModel
import ru.jsavings.R
import ru.jsavings.databinding.NewCategoryFragmentBinding
import ru.jsavings.domain.model.database.transaction.category.TransactionCategory
import ru.jsavings.domain.model.database.transaction.category.TransactionCategoryType
import ru.jsavings.presentation.ui.fragments.common.BaseFragment
import ru.jsavings.presentation.ui.fragments.common.BaseViewModel

class AddNewCategoryFragment : BaseFragment() {

	override val viewModel by viewModel<AddNewCategoryViewModel>()

	override lateinit var bindingUtil: NewCategoryFragmentBinding

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		bindingUtil = NewCategoryFragmentBinding.inflate(inflater, container, false)
		return bindingUtil.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		if (viewModel.color == 0) {
			viewModel.color = TypedValue().apply {
				requireActivity().theme.resolveAttribute(R.attr.colorPrimary, this, true)
			}.data
		}

		requestAllCategories()

		bindingUtil.tietNewCategoryName.addTextChangedListener {
			bindingUtil.tilNewCategoryName.isErrorEnabled = false
			it?.toString()?.let { newName -> viewModel.name = newName }
		}
		setUpCategoryTypes()
	}

	@Suppress("UNCHECKED_CAST")
	private fun requestAllCategories() {
		viewModel.requestAllCategoriesState.observe(viewLifecycleOwner) { state ->
		    when (state) {
		        is BaseViewModel.RequestState.SuccessState<*> -> {
		        	viewModel.allCategories.addAll(state.data as List<TransactionCategory>)
			        hideLoading()
				}
				is BaseViewModel.RequestState.ErrorState<*> -> {
					hideLoading()
				    showTextSnackBar(state.t.getErrorString())
				}
				else -> showLoading(getString(R.string.loading_transaction_categories))
			}
		}

		viewModel.requestAllCategories()
	}

	private fun setUpCategoryTypes() {

		data class CategoryTypeView(val type: TransactionCategoryType, val name: String) {
			override fun toString(): String = name
		}

		val listOfTypes = listOf(
			CategoryTypeView(TransactionCategoryType.INCOME, getString(R.string.transaction_type_income)),
			CategoryTypeView(TransactionCategoryType.CONSUMPTION, getString(R.string.transaction_type_consumption))
		)

		val adapter = ArrayAdapter(requireContext(), R.layout.item_dropdown_item, listOfTypes)

		bindingUtil.actvNewCategoryType.setAdapter(adapter)
		bindingUtil.actvNewCategoryType.setOnItemClickListener { _, _, position, _ ->
			bindingUtil.tilNewCategoryType.isErrorEnabled = false
			viewModel.type = listOfTypes[position].type.toString()
		}
	}

	private fun onSaveButtonClick() {
		showLoading(getString(R.string.loading_saving_category))

		val validationResult = viewModel.validateInputData()

		if (validationResult != AddNewCategoryViewModel.DATA_VALID) {

			fun TextInputLayout.checkAndShowError(checkCode: Int, stringId: Int) {
				if (viewModel.getValidationResult(validationResult, checkCode)) {
					error = getString(stringId)
					isErrorEnabled = true
				}
			}

			with(bindingUtil) {
				tilNewCategoryName.checkAndShowError(
					AddNewCategoryViewModel.NAME_EMPTY, R.string.new_category_error_name_is_empty
				)
				tilNewCategoryName.checkAndShowError(
					AddNewCategoryViewModel.NAME_TAKEN, R.string.new_category_error_name_is_taken
				)
				tilNewCategoryType.checkAndShowError(
					AddNewCategoryViewModel.TYPE_EMPTY, R.string.new_category_error_type_is_empty
				)
			}
			hideLoading()
			return
		}

		viewModel.requestSaveCategoryState.observe(viewLifecycleOwner) { state ->
		    when (state) {
		        is BaseViewModel.RequestState.SuccessState<*> -> {
			        hideLoading()
			        findNavController().popBackStack()
		        }
				is BaseViewModel.RequestState.ErrorState<*> -> {
					hideLoading()
				    showTextSnackBar(
					    text = state.t.getErrorString(),
					    actionText = getString(R.string.retry),
					    action = { onSaveButtonClick() }
				    )
				}
				else -> {}
			}
		}
	}
}
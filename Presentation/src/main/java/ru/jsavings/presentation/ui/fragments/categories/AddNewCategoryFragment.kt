package ru.jsavings.presentation.ui.fragments.categories

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.TextInputLayout
import com.pes.androidmaterialcolorpickerdialog.ColorPicker
import org.koin.android.viewmodel.ext.android.viewModel
import ru.jsavings.R
import ru.jsavings.databinding.FragmentNewCategoryBinding
import ru.jsavings.domain.model.database.category.TransactionCategory
import ru.jsavings.presentation.extension.isTextBlack
import ru.jsavings.presentation.ui.fragments.common.BaseFragment
import ru.jsavings.presentation.viewmodels.categories.AddNewCategoryViewModel

class AddNewCategoryFragment : BaseFragment() {

	override val viewModel by viewModel<AddNewCategoryViewModel>()
	private val bindingUtil get() = binding as FragmentNewCategoryBinding

	private val args by navArgs<AddNewCategoryFragmentArgs>()

	private val allCategories = mutableListOf<TransactionCategory>()

	override fun onAttach(context: Context) {
		super.onAttach(context)
		viewModel.type = args.transactionType
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		binding = FragmentNewCategoryBinding.inflate(inflater, container, false)
		return bindingUtil.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		if (viewModel.color == 0) {
			viewModel.color = TypedValue().apply {
				requireActivity().theme.resolveAttribute(R.attr.colorPrimary, this, true)
			}.data
		}

		setUpObservers()
		viewModel.requestAllCategories(args.accountId)

		with(bindingUtil) {
			hideKeyBoardOnRootTouchClick()

			buttonSaveNewCategory.isEnabled = false

			tietNewCategoryName.addTextChangedListener {
				tilNewCategoryName.isErrorEnabled = false
				it?.toString()?.let { newName ->
					buttonSaveNewCategory.isEnabled = newName.isNotBlank() && newName.isNotEmpty()
					viewModel.name = newName
				}
			}
			buttonNewTransactionCategoryColor.setOnClickListener(::onColorButtonClick)
			buttonSaveNewCategory.setOnClickListener { onSaveButtonClick() }
		}
	}

	private fun setUpObservers() {
		viewModel.requestAllCategoriesState.subscribe<List<TransactionCategory>>(
			hideLoading = true,
			onSuccess = {
				allCategories.addAll(it)
				if (args.categoryId != -1L)
					allCategories.find { category -> category.categoryId == args.categoryId }?.let {
						with(viewModel) {
							name = it.name
							color = it.color
						}
						with(bindingUtil) {
							buttonSaveNewCategory.text = getString(R.string.update_category)
							tietNewCategoryName.setText(it.name)
							buttonNewTransactionCategoryColor.setBackgroundColor(it.color)
						}
					}
			},
			onError = { showTextSnackBar(
				text = it.getErrorString(),
				actionText = getString(R.string.retry),
				action = { viewModel.requestAllCategories(args.accountId) }
			)},
			onSending = { showLoading(R.string.loading_transaction_categories) }
		)

		viewModel.requestSaveCategoryState.subscribe<Any>(
			hideLoading = true,
			onSuccess = {
				hideKeyBoard()
				findNavController().popBackStack()
			},
			onError = { showTextSnackBar(
				text = it.getErrorString(),
				actionText = getString(R.string.retry),
				action = { onSaveButtonClick() }
			)},
			onSending = { showLoading(R.string.loading_saving_category) }
		)
	}

	private fun onColorButtonClick(view: View) {
		ColorPicker(
			requireActivity(),
			Color.red(viewModel.color),
			Color.green(viewModel.color),
			Color.blue(viewModel.color)
		).apply {
			setCallback { color ->
				viewModel.color = color
				view.setBackgroundColor(color)
				bindingUtil.buttonSaveNewCategory.isEnabled = true
				bindingUtil.buttonNewTransactionCategoryColor.setTextColor(AppCompatResources.getColorStateList(
					requireContext(),
					if (color.isTextBlack()) R.color.black else R.color.white
				))
			}
			enableAutoClose()
			show()
		}
	}

	private fun onSaveButtonClick() {

		if (args.categoryId == -1L)
			showLoading(getString(R.string.loading_saving_category))
		else
			showLoading(getString(R.string.loading_updating_category))

		val validationResult = viewModel.validateInputData(allCategories)

		if (validationResult != AddNewCategoryViewModel.DATA_VALID) {

			fun TextInputLayout.checkAndShowError(checkCode: Int, stringId: Int) {
				if (validationResult == checkCode) {
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
			}
			hideLoading()
			return
		}

		if (args.categoryId == -1L)
			viewModel.requestSaveCategory(args.accountId)
		else
			viewModel.requestUpdateCategory(args.accountId, args.categoryId)
	}
}
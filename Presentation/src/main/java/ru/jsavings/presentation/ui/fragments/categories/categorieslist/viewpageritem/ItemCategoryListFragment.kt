package ru.jsavings.presentation.ui.fragments.categories.categorieslist.viewpageritem

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import org.koin.android.viewmodel.ext.android.viewModel
import ru.jsavings.R
import ru.jsavings.databinding.ItemFragmentCategoriesListBinding
import ru.jsavings.domain.model.database.category.TransactionCategory
import ru.jsavings.domain.model.database.category.TransactionCategoryType
import ru.jsavings.presentation.ui.fragments.categories.categorieslist.CategoriesListFragment
import ru.jsavings.presentation.ui.fragments.categories.categorieslist.CategoriesListFragmentDirections
import ru.jsavings.presentation.ui.fragments.categories.categorieslist.viewpageritem.recycler.CategoriesAdapter
import ru.jsavings.presentation.ui.fragments.common.BaseFragment
import java.util.*

/**
 * Fragment for categories list in viewpager2
 *
 * @author JustSpace
 */
class ItemCategoryListFragment : BaseFragment() {

	companion object {
		private const val ACCOUNT_ID = "ACCOUNT_ID"
		private const val CATEGORIES_LIST_KEY = "CATEGORIES_LIST_KEY"
		private const val CATEGORY_TYPE = "CATEGORY_TYPE"

		/**
		 * Get instance of this fragment
		 * @param accountId Id of account
		 * @param categoryType Type of category to show
		 * @param categoriesList List of categories to show
		 * @return New instance of this fragment
		 *
		 * @author JustSpace
		 */
		fun getInstance(
			accountId: Long,
			categoryType: TransactionCategoryType,
			categoriesList: List<TransactionCategory>,
		): ItemCategoryListFragment =
			ItemCategoryListFragment().apply {
				arguments = Bundle().apply {
					putLong(ACCOUNT_ID, accountId)
					putParcelableArrayList(CATEGORIES_LIST_KEY, ArrayList(categoriesList))
					putSerializable(CATEGORY_TYPE, categoryType)
				}
			}
	}

	override val viewModel by viewModel<ItemCategoryListViewModel>()
	override lateinit var bindingUtil: ItemFragmentCategoriesListBinding

	private var accountId: Long? = null
	private val categoriesList = mutableListOf<TransactionCategory>()

	override fun onAttach(context: Context) {
		super.onAttach(context)
		viewModel.transactionCategoryType = arguments?.getSerializable(CATEGORY_TYPE) as? TransactionCategoryType
			?: TransactionCategoryType.INCOME
		arguments?.getParcelableArrayList<TransactionCategory>(CATEGORIES_LIST_KEY)?.let { categoriesList.addAll(it) }
		accountId = arguments?.getLong(ACCOUNT_ID)
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		bindingUtil = ItemFragmentCategoriesListBinding.inflate(inflater, container, false)
		return bindingUtil.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		setObserveDeleteCategory()

		with(bindingUtil) {
			buttonAddNewCategory.setText(
				if (viewModel.transactionCategoryType == TransactionCategoryType.INCOME)
					R.string.create_new_income_category
				else
					R.string.create_new_consumption_category
			)

			if (categoriesList.isEmpty()) {
				rwTransactionCategories.isVisible = false
				ivNoCategories.isVisible = true
				textNoCategories.isVisible = true
			} else {
				bindingUtil.rwTransactionCategories.adapter = CategoriesAdapter(
					categoriesList,
					::onCategoryClick,
					::onCategoryEditClick,
					::onCategoryDeleteClick
				)
			}

			buttonAddNewCategory.setOnClickListener { onAddCategoryButtonClick() }
		}
	}

	private fun onCategoryClick(categoryId: Long) {
		requireActivity().supportFragmentManager.setFragmentResult(CategoriesListFragment.CATEGORY_CHOSEN, bundleOf(
			CategoriesListFragment.CHOSEN_CATEGORY_ID_KEY to categoryId
		))
		findNavController().popBackStack()
	}

	private fun onCategoryEditClick(categoryId: Long) {
		findNavController().navigate(
			CategoriesListFragmentDirections.actionCategoriesListFragmentToAddNewCategoryFragment(
				viewModel.transactionCategoryType, categoryId
			)
		)
	}

	private fun onCategoryDeleteClick(categoryId: Long) {
		AlertDialog.Builder(requireContext()).apply {
			setMessage(R.string.delete_category_question)
			setNegativeButton(R.string.no) { _, _ -> }
			setPositiveButton(R.string.yes) { _, _ -> viewModel.requestDeleteCategory(categoryId) }
			show()
		}
	}

	@SuppressLint("NotifyDataSetChanged")
	private fun setObserveDeleteCategory() = viewModel.requestDeleteCategoryState.subscribe<Long>(
		hideLoading = true,
		onSuccess = {
			val index = categoriesList.indexOfFirst { category -> category.categoryId == it }
			categoriesList.removeAt(index)
			bindingUtil.rwTransactionCategories.adapter?.notifyItemRemoved(index)
			if (categoriesList.isEmpty()) {
				bindingUtil.ivNoCategories.isVisible = true
				bindingUtil.textNoCategories.isVisible = true
			}
		},
		onError = { showTextSnackBar(it.getErrorString()) },
		onSending = { showLoading(R.string.loading_deleting_category) }
	)

	private fun onAddCategoryButtonClick() {
		findNavController().navigate(
			CategoriesListFragmentDirections
				.actionCategoriesListFragmentToAddNewCategoryFragment(viewModel.transactionCategoryType, accountId!!)
		)
	}
}
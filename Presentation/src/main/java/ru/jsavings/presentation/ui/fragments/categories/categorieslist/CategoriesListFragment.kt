package ru.jsavings.presentation.ui.fragments.categories.categorieslist

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import org.koin.android.viewmodel.ext.android.viewModel
import ru.jsavings.R
import ru.jsavings.databinding.FragmentListCategoriesBinding
import ru.jsavings.domain.model.database.transaction.category.TransactionCategory
import ru.jsavings.domain.model.database.transaction.category.TransactionCategoryType
import ru.jsavings.presentation.ui.fragments.categories.categorieslist.recycler.CategoriesAdapter
import ru.jsavings.presentation.ui.fragments.common.BaseFragment
import ru.jsavings.presentation.ui.fragments.common.BaseViewModel


class CategoriesListFragment : BaseFragment() {

	companion object {
		const val CATEGORY_CHOSEN = "CATEGORY_CHOSEN"
		const val CHOSEN_CATEGORY_ID_KEY = "CHOSEN_CATEGORY_ID_KEY"
	}

	override val viewModel by viewModel<CategoriesListViewModel>()

	override lateinit var bindingUtil: FragmentListCategoriesBinding

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		bindingUtil = FragmentListCategoriesBinding.inflate(inflater, container, false)
		return bindingUtil.root
	}

	@Suppress("UNCHECKED_CAST")
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		viewModel.requestAllTransactionsState.observe(viewLifecycleOwner) { state ->
		    when (state) {
		        is BaseViewModel.RequestState.SuccessState<*> -> {
			        viewModel.categoriesList.addAll(state.data as List<TransactionCategory>)
			        setUpAdapter()
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

		bindingUtil.buttonAddNewCategory.setOnClickListener {
			findNavController().navigate(
				CategoriesListFragmentDirections.actionCategoriesListFragmentToAddNewCategoryFragment()
			)
		}
	}

	private fun setUpAdapter() {

		val listOfCategories = mutableListOf<TransactionCategory>()

		val adapter = CategoriesAdapter(listOfCategories, ::onCategoryClick)
		bindingUtil.rwTransactionCategories.adapter = adapter

		bindingUtil.tabCategories.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

			@SuppressLint("NotifyDataSetChanged")
			override fun onTabSelected(tab: TabLayout.Tab?) {
				tab?.let {
					listOfCategories.clear()
					listOfCategories.addAll(
						if (it.id == R.id.income)
							viewModel.categoriesList.filter { category ->
								category.categoryType == TransactionCategoryType.INCOME
							}
						else
							viewModel.categoriesList.filter { category ->
								category.categoryType == TransactionCategoryType.CONSUMPTION
							}
					)
					adapter.notifyDataSetChanged()
				}
			}

			override fun onTabUnselected(tab: TabLayout.Tab?) {}

			override fun onTabReselected(tab: TabLayout.Tab?) {}
		})
	}

	private fun onCategoryClick(categoryId: Long) {
		setFragmentResult(CATEGORY_CHOSEN, bundleOf(
			CHOSEN_CATEGORY_ID_KEY to categoryId
		))
		findNavController().popBackStack()
	}
}
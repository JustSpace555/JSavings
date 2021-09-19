package ru.jsavings.presentation.ui.fragments.categories.categorieslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.android.viewmodel.ext.android.viewModel
import ru.jsavings.R
import ru.jsavings.databinding.FragmentListCategoriesBinding
import ru.jsavings.domain.model.database.category.TransactionCategory
import ru.jsavings.domain.model.database.category.TransactionCategoryType
import ru.jsavings.presentation.ui.fragments.categories.categorieslist.viewpageritem.ItemCategoryListFragment
import ru.jsavings.presentation.ui.fragments.common.BaseFragment
import ru.jsavings.presentation.viewmodels.categories.CategoriesListViewModel

class CategoriesListFragment : BaseFragment() {

	companion object {
		/**
		 * These keys are used to delegate chosen transaction category id between
		 * [ru.jsavings.presentation.ui.fragments.categories.categorieslist.viewpageritem.ItemCategoryListFragment] and
		 * [ru.jsavings.presentation.ui.fragments.transactions.NewTransactionFragment]
		 * through fragment result listener
		 *
		 * @author JustSpace
		 */
		const val CATEGORY_CHOSEN = "CATEGORY_CHOSEN"
		const val CHOSEN_CATEGORY_ID_KEY = "CHOSEN_CATEGORY_ID_KEY"
	}

	override val viewModel by viewModel<CategoriesListViewModel>()
	private val bindingUtil get() = binding as FragmentListCategoriesBinding

	private val args by navArgs<CategoriesListFragmentArgs>()

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		binding = FragmentListCategoriesBinding.inflate(inflater, container, false)
		return bindingUtil.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		observeRequestAllCategories()
		viewModel.requestAllCategories(args.accountId)
	}

	@Suppress("UNCHECKED_CAST")
	private fun observeRequestAllCategories() =
		viewModel.requestAllCategoriesState.subscribe<List<TransactionCategory>>(
			hideLoading = true,
			onSuccess = {
				bindingUtil.vpCategories.adapter = TabAdapter(it)
				TabLayoutMediator(bindingUtil.tabCategoriesTypes, bindingUtil.vpCategories) { tab, position ->
					tab.text = getString(
						if (position == 0) R.string.transaction_type_income else R.string.transaction_type_consumption
					)
				}.attach()
			},
			onError = {
				showTextSnackBar(
					it.getErrorString(),
					actionText = getString(R.string.retry),
					action = { viewModel.requestAllCategories(args.accountId) }
				)
			},
			onSending = { showLoading(R.string.loading_transaction_categories) }
		)

	private inner class TabAdapter(
		private val categoriesList: List<TransactionCategory>,
	) : FragmentStateAdapter(this) {

		override fun getItemCount() = 2

		override fun createFragment(position: Int): Fragment {
			val type = if (position == 0) TransactionCategoryType.INCOME else TransactionCategoryType.CONSUMPTION
			val categories = categoriesList.filter { it.categoryType == type }

			return ItemCategoryListFragment.getInstance(args.accountId, type, categories)
		}
	}
}
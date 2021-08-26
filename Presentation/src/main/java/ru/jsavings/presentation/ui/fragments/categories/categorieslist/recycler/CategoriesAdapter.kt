package ru.jsavings.presentation.ui.fragments.categories.categorieslist.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.jsavings.databinding.ItemTransactionCategoryBinding
import ru.jsavings.domain.model.database.transaction.category.TransactionCategory
import ru.jsavings.domain.model.database.transaction.category.TransactionCategoryType

class CategoriesAdapter(
	private val categoriesList: List<TransactionCategory>,
	private val onCategoryClick: (Long) -> Unit
) : RecyclerView.Adapter<CategoryViewHolder>() {

	private val categoriesFiltered = categoriesList.toMutableList()

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder = CategoryViewHolder(
		ItemTransactionCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
	)

	override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
		holder.apply {
			setUpHolder(categoriesFiltered[position])
			setOnCategoryClick(onCategoryClick)
		}
	}

	override fun getItemCount(): Int = categoriesFiltered.size
}
package ru.jsavings.presentation.ui.fragments.categories.categorieslist.viewpageritem.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.jsavings.databinding.ItemTransactionCategoryBinding
import ru.jsavings.domain.model.database.category.TransactionCategory

/**
 * Adapter of transaction categories
 * @param categoriesList List of categories
 * @param onCategoryClick Lambda function that invokes on category click
 * @param onCategoryEditClick Lambda function that invokes on category edit button click
 * @param onCategoryDeleteClick Lambda function that invokes on category delete click
 *
 * @author JustSpace
 */
class CategoriesAdapter(
	private val categoriesList: List<TransactionCategory>,
	private val onCategoryClick: (id: Long) -> Unit,
	private val onCategoryEditClick: (id: Long) -> Unit,
	private val onCategoryDeleteClick: (id: Long) -> Unit
) : RecyclerView.Adapter<CategoryViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder = CategoryViewHolder(
		ItemTransactionCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
	)

	override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
		holder.apply {
			setUpHolder(categoriesList[position])
			setOnCategoryClick(onCategoryClick)
			setOnCategoryEditClick(onCategoryEditClick)
			setOnCategoryDeleteClick(onCategoryDeleteClick)
		}
	}

	override fun getItemCount(): Int = categoriesList.size
}
package ru.jsavings.presentation.ui.fragments.categories.categorieslist.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.jsavings.BuildConfig
import ru.jsavings.databinding.ItemTransactionCategoryBinding
import ru.jsavings.domain.model.database.transaction.category.TransactionCategory
import ru.jsavings.domain.model.database.transaction.category.TransactionCategoryType

class CategoryViewHolder(
	private val bindingUtil: ItemTransactionCategoryBinding
) : RecyclerView.ViewHolder(bindingUtil.root) {

	fun setUpHolder(category: TransactionCategory) {
		with(bindingUtil) {
			textTransactionCategoryName.text = category.name
			textTransactionCategoryId.text = category.categoryId.toString()
			textTransactionCategoryType.text = category.categoryType.toString()

			if (BuildConfig.DEBUG) {
				textTransactionCategoryType.visibility = View.VISIBLE
				textTransactionCategoryId.visibility = View.VISIBLE
			}
			ivTransactionCategoryIcon.setBackgroundColor(category.color)
		}
	}

	fun setOnCategoryClick(onClick: (Long) -> Unit) = bindingUtil.root.setOnClickListener {
		onClick(
			bindingUtil.textTransactionCategoryId.text.toString().toLong()
		)
	}

}
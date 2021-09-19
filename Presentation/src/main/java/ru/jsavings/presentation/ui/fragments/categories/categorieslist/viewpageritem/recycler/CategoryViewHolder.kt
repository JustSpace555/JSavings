package ru.jsavings.presentation.ui.fragments.categories.categorieslist.viewpageritem.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.jsavings.BuildConfig
import ru.jsavings.databinding.ItemTransactionCategoryBinding
import ru.jsavings.domain.model.database.category.TransactionCategory

/**
 * ViewHolder for transaction category
 * @param bindingUtil ViewBinding for [ItemTransactionCategoryBinding]
 *
 * @author JustSpace
 */
class CategoryViewHolder(
	private var bindingUtil: ItemTransactionCategoryBinding
) : RecyclerView.ViewHolder(bindingUtil.root) {

	/**
	 * Setup view holder with transaction category data
	 * @param category [TransactionCategory] to display
	 *
	 * @author JustSpace
	 */
	fun setUpHolder(category: TransactionCategory) {
		with(bindingUtil) {
			textTransactionCategoryName.text = category.name
			textTransactionCategoryId.text = category.categoryId.toString()

			if (BuildConfig.DEBUG) {
				textTransactionCategoryId.visibility = View.VISIBLE
			}
			ivTransactionCategoryIcon.setBackgroundColor(category.color)
		}
	}

	/**
	 * Setup view holder with lambda that invokes on click
	 * @param onClick Lambda with category id param
	 *
	 * @author JustSpace
	 */
	fun setOnCategoryClick(onClick: (Long) -> Unit) = bindingUtil.root.setOnClickListener {
		onClick(
			bindingUtil.textTransactionCategoryId.text.toString().toLong()
		)
	}

	/**
	 * Setup view holder with lambda that invokes on edit button click
	 * @param onClick Lambda with position and category id params
	 *
	 * @author JustSpace
	 */
	fun setOnCategoryEditClick(onClick: (Long) -> Unit) {
		bindingUtil.ivEdit.setOnClickListener {
			onClick(bindingUtil.textTransactionCategoryId.text.toString().toLong())
		}
	}

	/**
	 * Setup view holder with lambda that invokes on delete button click
	 * @param onClick Lambda with position and category id params
	 *
	 * @author JustSpace
	 */
	fun setOnCategoryDeleteClick(onClick: (Long) -> Unit) {
		bindingUtil.ivDelete.setOnClickListener {
			onClick(bindingUtil.textTransactionCategoryId.text.toString().toLong())
		}
	}
}
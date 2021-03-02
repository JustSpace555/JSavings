package ru.jsavings.repository.entity.category

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.jsavings.model.entity.category.Category

interface CategoryRepository {

	fun getCategoryById(id: Int): Single<Category>

	fun getCategoriesByIdList(ids: List<Int>): Single<List<Category>>

	fun addNewCategory(category: Category): Completable

	fun updateCategory(category: Category): Completable

	fun deleteCategory(category: Category): Completable
}
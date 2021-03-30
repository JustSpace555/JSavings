package ru.jsavings.data.repository.category

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.database.dao.CategoryDao
import ru.jsavings.data.entity.category.Category
import java.lang.Exception

class CategoryRepositoryImpl(private val categoryDao: CategoryDao) : CategoryRepository {

	override fun getCategoryById(id: Int): Single<Category> = Single.create { subscriber ->
		try {
			subscriber.onSuccess(categoryDao.getCategoryById(id))
		} catch (e: Exception) {
			subscriber.onError(e)
		}
	}

	override fun getCategoriesByIdList(ids: List<Int>): Single<List<Category>> = Single.create { subscriber ->
		try {
			subscriber.onSuccess(categoryDao.getCategoriesByIdList(ids))
		} catch (e: Exception) {
			subscriber.onError(e)
		}
	}

	override fun addNewCategory(category: Category): Completable = Completable.create { subscriber ->
		try {
			categoryDao.addNewCategory(category)
			subscriber.onComplete()
		} catch (e: Exception) {
			subscriber.onError(e)
		}
	}

	override fun updateCategory(category: Category): Completable = Completable.create { subscriber ->
		try {
			categoryDao.updateCategory(category)
			subscriber.onComplete()
		} catch (e: Exception) {
			subscriber.onError(e)
		}
	}

	override fun deleteCategory(category: Category): Completable = Completable.create { subscriber ->
		try {
			categoryDao.deleteCategory(category)
			subscriber.onComplete()
		} catch (e: Exception) {
			subscriber.onError(e)
		}
	}
}
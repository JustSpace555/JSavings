package ru.jsavings.domain.model.database.category

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.jsavings.domain.model.common.BaseModel

/**
 * Model for database [ru.jsavings.data.entity.database.TransactionCategoryEntity]
 * @param categoryId Id of category in database
 * @param name Name of category
 * @param accountId Id of account to which category belongs
 * @param categoryType Type of category
 * @param color Number representation of color
 * @param iconPath Path to icon of category
 *
 * @author JustSpace
 */
@Parcelize
data class TransactionCategory (
	val categoryId: Long = 0,
	val name: String,
	val accountId: Long,
	val categoryType: TransactionCategoryType,
	val color: Int,
	val iconPath: String
) : BaseModel, Parcelable
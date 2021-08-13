package ru.jsavings.data.entity

import androidx.annotation.ColorInt
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import ru.jsavings.data.entity.common.BaseEntity

/**
 * Entity of transaction category in database
 * @param categoryId Id of category in database
 * @param categoryName Name of category
 * @param accountFkId Id of [AccountEntity] to which this category belongs
 * @param type Type of category: INCOME, CONSUMPTION, TRANSFER
 * @param color Number representation of color
 * @param iconPath Path to icon of this category
 *
 * @author JustSpace
 */
@Entity (
	tableName = "transaction_category_table",
	foreignKeys = [
		ForeignKey(
			entity = AccountEntity::class,
			childColumns = ["account_fk_id"],
			parentColumns = ["account_id"],
			onDelete = ForeignKey.CASCADE
		)
	]
)
internal data class TransactionCategoryEntity (

	@PrimaryKey
	@ColumnInfo(name = "category_id")
	val categoryId: Long = 0,

	@ColumnInfo(name = "category_name")
	val categoryName: String,

	@ColumnInfo(name = "account_fk_id", index = true)
	val accountFkId: Int,

	val type: String,

	@ColorInt
	val color: Int,

	@ColumnInfo(name = "icon_path")
	val iconPath: String

) : BaseEntity
package ru.jsavings.data.entity.transaction

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import ru.jsavings.data.entity.AccountEntity
import ru.jsavings.data.entity.BaseEntity

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
	val categoryId: Int = 0,

	//Name of the category
	@ColumnInfo(name = "category_name")
	val categoryName: String,

	//The name of the account which the category is linked
	@ColumnInfo(name = "account_fk_id", index = true)
	val accountFkId: Int,

	//Category type (TransactionCategoryType)
	val type: String,

	//Category color (0xFFFFFF)
	val color: String,

	//Path to icon
	@ColumnInfo(name = "icon_path")
	val iconPath: String

) : BaseEntity
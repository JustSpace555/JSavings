package ru.jsavings.data.entity.transaction

import androidx.room.*
import ru.jsavings.data.entity.AccountEntity
import ru.jsavings.data.entity.BaseEntity

@Entity (
	tableName = "transaction_category_table",
	foreignKeys = [
		ForeignKey(entity = AccountEntity::class, childColumns = ["account_fk_name"], parentColumns = ["account_name"])
	]
)
internal data class TransactionCategoryEntity (

	//Name of the category
	@PrimaryKey
	@ColumnInfo(name = "category_name")
	val categoryName: String,

	//The name of the account which the category is linked
	@ColumnInfo(name = "account_fk_name", index = true)
	val accountFkName: String,

	//Category type (TransactionCategoryType)
	val type: String,

	//Category color (0xFFFFFF)
	val color: String,

	//Path to icon
	@ColumnInfo(name = "icon_path")
	val iconPath: String

) : BaseEntity
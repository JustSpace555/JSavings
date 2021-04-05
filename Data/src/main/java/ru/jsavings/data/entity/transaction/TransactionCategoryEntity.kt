package ru.jsavings.data.entity.transaction

import androidx.room.*
import ru.jsavings.data.entity.AccountEntity
import ru.jsavings.data.entity.BaseEntity

@Entity (
	tableName = "transaction_category_table",
	foreignKeys = [
		ForeignKey(entity = AccountEntity::class, childColumns = ["account_fk_id"], parentColumns = ["account_id"])
	]
)
internal data class TransactionCategoryEntity (

	//Category id
	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "category_id")
	val categoryId: Int,

	@ColumnInfo(name = "account_fk_id")
	val accountFkId: Int,

	//Category name
	val name: String,

	//Category type (TransactionCategoryType)
	val type: String,

	//Category color (0xFFFFFF)
	val color: String,

	//Path to icon
	@ColumnInfo(name = "icon_path")
	val iconPath: String
) : BaseEntity
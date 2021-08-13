package ru.jsavings.data.entity.database.account

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
<<<<<<< refs/remotes/origin/dev:Data/src/main/java/ru/jsavings/data/entity/database/account/AccountEntity.kt
import ru.jsavings.data.entity.database.common.BaseDbEntity
=======
import ru.jsavings.data.entity.common.BaseEntity
>>>>>>> Rework started:Data/src/main/java/ru/jsavings/data/entity/AccountEntity.kt

/**
 * Entity of account in database
 * @param accountId Id of account in database
 * @param accountName Name of account
 * @param mainCurrencyCode Code of main currency of account, according to api or [java.util.Currency] (usd, bitcoin, ...)
 * @param balanceInMainCurrency Balance of account in currency which id is [mainCurrencyCode]
 *
 * @author JustSpace
 */
@Entity (tableName = "account_table")
internal data class AccountEntity (

	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "account_id")
	val accountId: Long = 0,

	@ColumnInfo(name = "account_name")
	val accountName: String,

<<<<<<< refs/remotes/origin/dev:Data/src/main/java/ru/jsavings/data/entity/database/account/AccountEntity.kt
	//Main currency of this account
	@ColumnInfo(name = "main_currency_code")
=======
	@ColumnInfo(name = "main_currency")
>>>>>>> Rework started:Data/src/main/java/ru/jsavings/data/entity/AccountEntity.kt
	val mainCurrencyCode: String,

	@ColumnInfo(name = "balance_in_main_currency")
	val balanceInMainCurrency: Double

) : BaseDbEntity
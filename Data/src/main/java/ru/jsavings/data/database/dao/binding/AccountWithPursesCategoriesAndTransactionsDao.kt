package ru.jsavings.data.database.dao.binding

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import ru.jsavings.data.entity.binding.AccountWithPursesCategoriesAndTransactionsEntity

@Dao
internal interface AccountWithPursesCategoriesAndTransactionsDao {

	@Transaction
	@Query("SELECT * FROM account_table")
	fun getAllAccountsWithPursesCategoriesAndTransactions(): List<AccountWithPursesCategoriesAndTransactionsEntity>
}
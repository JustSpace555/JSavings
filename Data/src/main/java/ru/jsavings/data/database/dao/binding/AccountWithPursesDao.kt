package ru.jsavings.data.database.dao.binding

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import ru.jsavings.data.database.dao.BaseDao
import ru.jsavings.data.entity.database.binding.AccountWithPursesEntity

@Dao
internal interface AccountWithPursesDao : BaseDao {

	@Transaction
	@Query("SELECT * FROM account_table")
	fun getAllAccountsWithPurses(): List<AccountWithPursesEntity>
}
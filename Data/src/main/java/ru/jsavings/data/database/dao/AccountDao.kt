package ru.jsavings.data.database.dao

import androidx.room.*
import ru.jsavings.data.entity.AccountEntity

@Dao
internal interface AccountDao : BaseDao {

	@Query("SELECT * FROM account_table")
	fun getAllAccounts(): List<AccountEntity>

	@Insert
	fun createNewAccount(accountEntity: AccountEntity)

	@Update
	fun updateAccount(accountEntity: AccountEntity)

	@Delete
	fun deleteAccount(accountEntity: AccountEntity)
}
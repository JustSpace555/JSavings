package ru.jsavings.data.database.dao

import androidx.room.*
import ru.jsavings.data.entity.database.account.AccountEntity

@Dao
internal interface AccountDao : BaseDao {

	@Query("SELECT * FROM account_table")
	fun getAllAccounts(): List<AccountEntity>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun createNewAccount(accountEntity: AccountEntity): Long

	@Update
	fun updateAccount(accountEntity: AccountEntity)

	@Delete
	fun deleteAccounts(accountEntities: List<AccountEntity>)

	@Query("SELECT * FROM account_table WHERE account_id = :id")
	fun getAccountById(id: Long): AccountEntity
}
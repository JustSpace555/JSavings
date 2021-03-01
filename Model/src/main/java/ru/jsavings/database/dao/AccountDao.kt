package ru.jsavings.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import ru.jsavings.model.entity.Account

@Dao
interface AccountDao {

	@Query("SELECT * FROM account")
	fun getAllAccounts(): List<Account>

	@Query("SELECT * FROM account WHERE account.account_id = :id")
	fun getAccountById(id: Int): Account

	@Update
	fun updateAccount(account: Account)

	@Delete
	fun deleteAccount(account: Account)
}
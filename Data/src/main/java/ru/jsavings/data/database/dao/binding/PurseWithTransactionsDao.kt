package ru.jsavings.data.database.dao.binding

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import ru.jsavings.data.entity.binding.PurseWithTransactionsEntity

@Dao
internal interface PurseWithTransactionsDao {

	@Transaction
	@Query("SELECT * FROM purse_table")
	fun getPurseWithTransaction(): List<PurseWithTransactionsEntity>
}
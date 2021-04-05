package ru.jsavings.data.database.dao

import androidx.room.*
import ru.jsavings.data.entity.PurseEntity

@Dao
internal interface PurseDao : BaseDao {

	@Query("SELECT * FROM purse_table WHERE purse_id = :inputId")
	fun getPurseById(inputId: Int): PurseEntity

	@Query("SELECT * FROM purse_table WHERE account_fk_id = :inputId")
	fun getPursesByAccountId(inputId: Int): List<PurseEntity>

	@Insert
	fun addNewPurse(purseEntity: PurseEntity)

	@Update
	fun updatePurse(purseEntity: PurseEntity)

	@Delete
	fun deletePurse(purseEntity: PurseEntity)
}
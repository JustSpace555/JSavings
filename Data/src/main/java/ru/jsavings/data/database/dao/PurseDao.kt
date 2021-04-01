package ru.jsavings.data.database.dao

import androidx.room.*
import ru.jsavings.data.entity.PurseEntity

@Dao
interface PurseDao : BaseDao {

	@Query("SELECT * FROM purse_table WHERE purse_id = :inputId")
	fun getPurseById(inputId: Int): PurseEntity

	@Insert
	fun addNewPurse(purseEntity: PurseEntity): Int

	@Update
	fun updatePurse(purseEntity: PurseEntity)

	@Delete
	fun deletePurse(purseEntity: PurseEntity)
}
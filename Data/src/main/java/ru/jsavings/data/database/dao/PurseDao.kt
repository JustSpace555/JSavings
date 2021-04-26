package ru.jsavings.data.database.dao

import androidx.room.*
import ru.jsavings.data.entity.database.purse.PurseEntity

@Dao
internal interface PurseDao : BaseDao {

	@Query("SELECT * FROM purse_table WHERE account_fk_id = :inputId")
	fun getPursesInAccountEntityName(inputId: Int): List<PurseEntity>

	@Insert
	fun addNewPurse(purseEntity: PurseEntity)

	@Update
	fun updatePurse(purseEntity: PurseEntity)

	@Delete
	fun deletePurse(purseEntity: PurseEntity)
}
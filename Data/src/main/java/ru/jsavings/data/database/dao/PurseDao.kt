package ru.jsavings.data.database.dao

import androidx.room.*
import ru.jsavings.data.entity.AccountEntity
import ru.jsavings.data.entity.PurseEntity

@Dao
internal interface PurseDao : BaseDao {

	@Query("SELECT * FROM purse_table WHERE purse_name = :inputName")
	fun getPurseByName(inputName: String): PurseEntity

	@Query(
		"SELECT * FROM purse_table WHERE account_fk_name = :inputName"
	)
	fun getPursesInAccountEntityName(inputName: String): List<PurseEntity>

	@Insert
	fun addNewPurse(purseEntity: PurseEntity)

	@Update
	fun updatePurse(purseEntity: PurseEntity)

	@Delete
	fun deletePurse(purseEntity: PurseEntity)
}
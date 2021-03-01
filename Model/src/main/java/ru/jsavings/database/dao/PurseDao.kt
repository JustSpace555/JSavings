package ru.jsavings.database.dao

import androidx.room.*
import ru.jsavings.model.entity.Purse

@Dao
interface PurseDao {

	@Query("SELECT * FROM purse WHERE purse.purse_id = :id")
	fun getPurseById(id: Int): Purse

	@Query("SELECT * FROM purse WHERE purse.purse_id IN (:idList)")
	fun getPursesByIdList(idList: List<Int>): List<Purse>

	@Insert
	fun addNewPurse(purse: Purse)

	@Update
	fun updatePurse(purse: Purse)

	@Delete
	fun deletePurse(purse: Purse)
}
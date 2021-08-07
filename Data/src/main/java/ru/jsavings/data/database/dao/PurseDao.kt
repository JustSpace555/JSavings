package ru.jsavings.data.database.dao

import androidx.room.*
import ru.jsavings.data.entity.database.purse.PurseEntity

@Dao
internal interface PurseDao : BaseDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun addNewPurse(purseEntity: PurseEntity): Long

	@Update
	fun updatePurse(purseEntity: PurseEntity)

	@Delete
	fun deletePurse(purseEntity: PurseEntity)
}
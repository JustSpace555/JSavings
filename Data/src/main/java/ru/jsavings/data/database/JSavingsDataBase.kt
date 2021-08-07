package ru.jsavings.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.jsavings.data.database.dao.AccountDao
import ru.jsavings.data.database.dao.PurseDao
import ru.jsavings.data.database.dao.TransactionCategoryDao
import ru.jsavings.data.database.dao.TransactionDao
import ru.jsavings.data.entity.database.account.AccountEntity
import ru.jsavings.data.entity.database.purse.PurseEntity
import ru.jsavings.data.entity.database.transaction.TransactionCategoryEntity
import ru.jsavings.data.entity.database.transaction.TransactionEntity

@Database(
	entities = [
		AccountEntity::class, PurseEntity::class, TransactionEntity::class, TransactionCategoryEntity::class
	],
	version = 1
)
internal abstract class JSavingsDataBase : RoomDatabase() {

	abstract fun accountDao(): AccountDao
	abstract fun categoryDao(): TransactionCategoryDao
	abstract fun purseDao(): PurseDao
	abstract fun transactionDao(): TransactionDao
}
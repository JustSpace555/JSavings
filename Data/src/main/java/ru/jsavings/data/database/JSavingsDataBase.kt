package ru.jsavings.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.jsavings.data.database.dao.AccountDao
import ru.jsavings.data.database.dao.TransactionCategoryDao
import ru.jsavings.data.database.dao.PurseDao
import ru.jsavings.data.database.dao.TransactionDao
import ru.jsavings.data.entity.AccountEntity
import ru.jsavings.data.entity.PurseEntity
import ru.jsavings.data.entity.transaction.TransactionEntity
import ru.jsavings.data.entity.transaction.TransactionCategoryEntity

@Database (
	entities = [
		AccountEntity::class, PurseEntity::class, TransactionEntity::class, TransactionCategoryEntity::class
	],
	version = 1
)
abstract class JSavingsDataBase : RoomDatabase() {
	abstract fun accountDao(): AccountDao

	abstract fun categoryDao(): TransactionCategoryDao

	abstract fun purseDao(): PurseDao

	abstract fun transactionDao(): TransactionDao
}
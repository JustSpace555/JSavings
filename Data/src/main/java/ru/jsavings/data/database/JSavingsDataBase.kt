package ru.jsavings.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.jsavings.data.database.dao.AccountDao
import ru.jsavings.data.database.dao.TransactionCategoryDao
import ru.jsavings.data.database.dao.WalletDao
import ru.jsavings.data.database.dao.TransactionDao
import ru.jsavings.data.entity.AccountEntity
import ru.jsavings.data.entity.WalletEntity
import ru.jsavings.data.entity.TransactionEntity
import ru.jsavings.data.entity.TransactionCategoryEntity

/**
 * Main data base of JSavings app
 * @see AccountEntity
 * @see WalletEntity
 * @see TransactionEntity
 * @see TransactionCategoryEntity
 * @see AccountDao
 * @see WalletDao
 * @see TransactionDao
 * @see TransactionCategoryDao
 *
 * @author JustSpace
 */
@Database (
	entities = [
		AccountEntity::class, WalletEntity::class, TransactionEntity::class, TransactionCategoryEntity::class
	],
	version = 1
)
internal abstract class JSavingsDataBase : RoomDatabase() {

	abstract fun accountDao(): AccountDao
	abstract fun walletDao(): WalletDao
	abstract fun transactionDao(): TransactionDao
	abstract fun categoryDao(): TransactionCategoryDao
}
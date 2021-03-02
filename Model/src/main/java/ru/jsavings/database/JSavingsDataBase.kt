package ru.jsavings.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.jsavings.database.dao.AccountDao
import ru.jsavings.database.dao.CategoryDao
import ru.jsavings.database.dao.PurseDao
import ru.jsavings.database.dao.TransactionDao
import ru.jsavings.model.converter.*
import ru.jsavings.model.entity.Account
import ru.jsavings.model.entity.category.Category
import ru.jsavings.model.entity.Purse
import ru.jsavings.model.entity.Transaction

@Database(entities = [Account::class, Purse::class, Transaction::class, Category::class], version = 1)
@TypeConverters(
	CategoryTypeConverter::class, CurrencyConverter::class, DateConverter::class, IdListConverter::class
)
abstract class JSavingsDataBase : RoomDatabase() {
	abstract fun accountDao(): AccountDao

	abstract fun categoryDao(): CategoryDao

	abstract fun purseDao(): PurseDao

	abstract fun transactionDao(): TransactionDao
}
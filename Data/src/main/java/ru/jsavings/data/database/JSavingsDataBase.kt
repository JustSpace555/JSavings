package ru.jsavings.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.jsavings.data.database.dao.AccountDao
import ru.jsavings.data.database.dao.CategoryDao
import ru.jsavings.data.database.dao.PurseDao
import ru.jsavings.data.database.dao.TransactionDao
import ru.jsavings.data.database.converters.CategoryTypeConverter
import ru.jsavings.data.database.converters.CurrencyConverter
import ru.jsavings.data.database.converters.DateConverter
import ru.jsavings.data.database.converters.IdListConverter
import ru.jsavings.data.entity.Account
import ru.jsavings.data.entity.Purse
import ru.jsavings.data.entity.Transaction
import ru.jsavings.data.entity.category.Category

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
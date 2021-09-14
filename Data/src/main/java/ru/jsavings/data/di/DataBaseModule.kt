package ru.jsavings.data.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.jsavings.data.database.JSavingsDataBase

/**
 * Koin module for database DAOes and database itself in [ru.jsavings.data] module
 *
 * @author JustSpace
 */
internal val dataBaseModule = module {
	single { Room.databaseBuilder(androidContext(), JSavingsDataBase::class.java, "JSavingsDB").build() }

	single { get<JSavingsDataBase>().accountDao() }
	single { get<JSavingsDataBase>().categoryDao() }
	single { get<JSavingsDataBase>().walletDao() }
	single { get<JSavingsDataBase>().transactionDao() }
}
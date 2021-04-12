package ru.jsavings.data.di

import androidx.room.Room
import org.koin.dsl.module
import ru.jsavings.data.database.JSavingsDataBase

internal val dataBaseModule = module {
	single { Room.databaseBuilder(get(), JSavingsDataBase::class.java, "JSavingsDB").build() }

	single { get<JSavingsDataBase>().accountDao() }
	single { get<JSavingsDataBase>().categoryDao() }
	single { get<JSavingsDataBase>().purseDao() }
	single { get<JSavingsDataBase>().transactionDao() }

	single { get<JSavingsDataBase>().accountWithPursesDao() }
}
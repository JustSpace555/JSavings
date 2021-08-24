package ru.jsavings.domain.di

import org.koin.dsl.module
import ru.jsavings.domain.mappers.database.AccountMapper
import ru.jsavings.domain.mappers.database.TransactionMapper
import ru.jsavings.domain.mappers.database.WalletMapper
import ru.jsavings.domain.mappers.network.ConversionMapper
import ru.jsavings.domain.mappers.network.CryptoCoinsMapper

/**
 * Koin module for all mappers in [ru.jsavings.domain.mappers] package
 *
 * @author JustSpace
 */
internal val mapperModule = module {

	//Database
	factory { AccountMapper() }
	factory { WalletMapper() }
	factory { TransactionMapper(get(), get()) }

	//Network
	factory { CryptoCoinsMapper() }
	factory { ConversionMapper() }
}
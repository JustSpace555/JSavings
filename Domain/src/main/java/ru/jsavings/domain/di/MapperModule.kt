package ru.jsavings.domain.di

import org.koin.dsl.module
import ru.jsavings.domain.mappers.database.*
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
	factory { TransactionCategoryMapper() }

	//Network
	factory { CryptoCoinsMapper() }
	factory { ConversionMapper() }
}
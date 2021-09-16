package ru.jsavings.domain.di

import org.koin.dsl.module
import ru.jsavings.domain.usecase.cache.CacheUseCase
import ru.jsavings.domain.usecase.database.account.GetAccountByIdUseCase
import ru.jsavings.domain.usecase.database.account.GetAllAccountsUseCase
import ru.jsavings.domain.usecase.database.account.InsertAccountUseCase
import ru.jsavings.domain.usecase.database.category.*
import ru.jsavings.domain.usecase.database.transaction.*
import ru.jsavings.domain.usecase.database.wallet.GetWalletByIdUseCase
import ru.jsavings.domain.usecase.database.wallet.GetWalletsByAccountIdUseCase
import ru.jsavings.domain.usecase.database.wallet.InsertNewWalletUseCase
import ru.jsavings.domain.usecase.network.ConvertCurrencyUseCase
import ru.jsavings.domain.usecase.network.GetCryptoCoinsUseCase
import ru.jsavings.domain.usecase.network.GetCurrenciesUseCase

/**
 * Koin module for all usecases in [ru.jsavings.domain.usecase] package
 *
 * @author JustSpace
 */
internal val useCaseModule = module {

	//Account
	factory { GetAllAccountsUseCase(get(), get()) }
	factory { InsertAccountUseCase(get(), get()) }
	factory { GetAccountByIdUseCase(get(), get()) }

	//Wallet
	factory { InsertNewWalletUseCase(get(), get(), get(), get()) }
	factory { GetWalletsByAccountIdUseCase(get(), get()) }
	factory { GetWalletByIdUseCase(get(), get()) }

	//Transaction
	factory { SaveNewTransactionUseCase(get(), get(), get(), get(), get(), get()) }
	factory { GetAllTransactionsByAccountIdUseCase(get(), get()) }
	factory { GetTransactionByIdUseCase(get(), get()) }
	factory { DeleteTransactionByIdUseCase(get()) }
	factory { UpdateTransactionUseCase(get(), get()) }

	//TransactionCategory
	factory { GetAllCategoriesByAccountIdUseCase(get(), get()) }
	factory { InsertNewCategoryUseCase(get(), get()) }
	factory { GetTransactionCategoryByIdUseCase(get(), get()) }
	factory { DeleteTransactionCategoryUseCase(get()) }
	factory { UpdateTransactionCategoryUseCase(get(), get()) }

	//Cache
	factory { CacheUseCase(get()) }



	//Currencies
	factory { GetCurrenciesUseCase(get()) }

	//Coins
	factory { GetCryptoCoinsUseCase(get(), get()) }

	//General
	factory { ConvertCurrencyUseCase(get(), get()) }
}
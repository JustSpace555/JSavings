package ru.jsavings.domain.common

import ru.jsavings.data.entity.database.*
import ru.jsavings.data.entity.network.*
import ru.jsavings.domain.mappers.database.TransactionMapperTest
import ru.jsavings.domain.model.database.Account
import ru.jsavings.domain.model.database.category.TransactionCategory
import ru.jsavings.domain.model.database.category.TransactionCategoryType
import ru.jsavings.domain.model.database.transaction.Transaction
import ru.jsavings.domain.model.database.wallet.Wallet
import ru.jsavings.domain.model.database.wallet.WalletType
import ru.jsavings.domain.model.network.ConversionInfo
import ru.jsavings.domain.model.network.crypto.CryptoCoin
import java.util.*

abstract class BaseUnitTest {

	companion object {
		
		//Account
		const val ACCOUNT_ID = 1L
		const val ACCOUNT_NAME = "account name"
		const val ACCOUNT_CURRENCY = "account currency"
		const val ACCOUNT_BALANCE = 25.0
		
		//Wallet
		const val WALLET_BALANCE = 25.0
		val WALLET_CATEGORY = WalletType.CASH
		const val WALLET_COLOR = 0
		const val CREDIT_LIMIT = 0.0
		const val GRACE_PERIOD = 0
		const val WALLET_CURRENCY_CODE = "USD"
		const val WALLET_ICON_PATH = ""
		const val INTEREST_RATE = 0.0
		const val PAYMENT_DAY = 0
		const val WALLET_NAME = "some name"
		const val WALLET_ID = 1L

		//Transaction category
		const val CATEGORY_ID = 1L
		const val CATEGORY_ENTITY_TYPE = "INCOME"
		val CATEGORY_MODEL_TYPE = TransactionCategoryType.INCOME
		const val CATEGORY_COLOR = 0
		const val CATEGORY_ICON_PATH = ""
		const val CATEGORY_NAME = "name"

		//Transaction
		const val TRANSACTION_ID = 1L
		const val DATE_DAY = 0L
		const val DATE_TIME = 0L
		const val TRANSACTION_PICTURE_PATH = "path"
		const val DESCRIPTION = "description"
		const val ACCOUNT_SUM = 10.0
		const val WALLET_SUM = 15.0
		const val TRANSFER_SUM = 5.0
		
		//Crypto coin
		const val CRYPTO_COIN_ID = "some id"
		const val CRYPTO_COIN_NAME = "some name"
		const val CRYPTO_COIN_SYMBOL = "some symbol"
	}

	protected val accountEntity = AccountEntity(
		accountId = ACCOUNT_ID,
		accountName = ACCOUNT_NAME,
		mainCurrencyCode = ACCOUNT_CURRENCY,
		balanceInMainCurrency = ACCOUNT_BALANCE
	)

	protected val accountModel = Account(
		accountId = ACCOUNT_ID,
		name = ACCOUNT_NAME,
		mainCurrencyCode = ACCOUNT_CURRENCY,
		balanceInMainCurrency = ACCOUNT_BALANCE
	)

	protected val walletModel = Wallet(
		accountId = ACCOUNT_ID,
		balance = WALLET_BALANCE,
		type = WALLET_CATEGORY,
		color = WALLET_COLOR,
		creditLimit = CREDIT_LIMIT,
		gracePeriod = GRACE_PERIOD,
		currency = WALLET_CURRENCY_CODE,
		iconPath = WALLET_ICON_PATH,
		interestRate = INTEREST_RATE,
		paymentDay = PAYMENT_DAY,
		name = WALLET_NAME,
		walletId = WALLET_ID
	)

	protected val walletEntity = WalletEntity(
		accountFkId = ACCOUNT_ID,
		balance = WALLET_BALANCE,
		category = WALLET_CATEGORY.toString(),
		color = WALLET_COLOR,
		creditLimit = CREDIT_LIMIT,
		gracePeriod = GRACE_PERIOD,
		currencyCode = WALLET_CURRENCY_CODE,
		iconPath = WALLET_ICON_PATH,
		interestRate = INTEREST_RATE,
		paymentDay = PAYMENT_DAY,
		walletName = WALLET_NAME,
		walletId = WALLET_ID
	)

	protected val categoryEntity = TransactionCategoryEntity(
		accountFkId = ACCOUNT_ID,
		categoryId = CATEGORY_ID,
		type = CATEGORY_ENTITY_TYPE,
		color = CATEGORY_COLOR,
		iconPath = CATEGORY_ICON_PATH,
		categoryName = CATEGORY_NAME
	)

	protected val categoryModel = TransactionCategory(
		accountId = ACCOUNT_ID,
		categoryId = CATEGORY_ID,
		categoryType = CATEGORY_MODEL_TYPE,
		color = CATEGORY_COLOR,
		iconPath = CATEGORY_ICON_PATH,
		name = CATEGORY_NAME
	)

	protected val transactionModel = Transaction(
		accountId = ACCOUNT_ID,
		category = categoryModel,
		dateDay = Date(DATE_DAY),
		dateTime = Date(DATE_TIME),
		describePicturePath = TRANSACTION_PICTURE_PATH,
		description = DESCRIPTION,
		fromWallet = walletModel,
		toWallet = walletModel,
		sumInAccountCurrency = ACCOUNT_SUM,
		transferSum = TRANSFER_SUM,
		sumInWalletCurrency = WALLET_SUM,
		transactionId = TRANSACTION_ID
	)

	protected val transactionEntity = TransactionEntity(
		accountFkId = ACCOUNT_ID,
		categoryFkId = CATEGORY_ID,
		dateDay = DATE_DAY,
		dateTime = DATE_TIME,
		describePicturePath = TRANSACTION_PICTURE_PATH,
		description = DESCRIPTION,
		fromWalletFkId = WALLET_ID,
		toWalletFkId = WALLET_ID,
		sumInWalletCurrency = WALLET_SUM,
		sumInAccountCurrency = ACCOUNT_SUM,
		transferSum = TRANSFER_SUM,
		transactionId = TRANSACTION_ID
	)

	protected val transactionGroupEntity = TransactionGroupEntity(
		transactionEntity = transactionEntity,
		fromWalletEntity = walletEntity,
		toWalletEntity = walletEntity,
		categoryEntity = categoryEntity
	)

	protected val cryptoCoinInfoEntity = CryptoCoinInfoEntity(
		name = CRYPTO_COIN_NAME,
		symbol = CRYPTO_COIN_SYMBOL
	)

	protected val cryptoCoinEntity = CryptoCoinEntity(
		success = true,
		cryptocurrencies = mapOf(CRYPTO_COIN_ID to cryptoCoinInfoEntity)
	)

	protected val cryptoCoinModel = CryptoCoin(
		id = CRYPTO_COIN_ID,
		name = CRYPTO_COIN_NAME,
		symbol = CRYPTO_COIN_SYMBOL
	)
}
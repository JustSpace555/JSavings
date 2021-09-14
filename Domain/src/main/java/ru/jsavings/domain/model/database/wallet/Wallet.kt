package ru.jsavings.domain.model.database.wallet

import ru.jsavings.domain.model.common.BaseModel

/**
 * Model to [ru.jsavings.data.entity.database.WalletEntity]
 * @param walletId Id of wallet in data base
 * @param name Name of wallet
 * @param accountId Id of account in database to which wallet belongs
 * @param balance Amount of money in currency which id is [currency]
 * @param currency Id or code of currency according to api or [java.util.Currency]
 * @param type Category of wallet
 * @param creditLimit Credit limit for wallet (valid if wallet type is [WalletType.CREDIT_CARD])
 * @param interestRate Interest rate of wallet (valid if wallet type is [WalletType.CREDIT_CARD])
 * @param paymentDay Day of payment for wallet (valid if wallet type is [WalletType.CREDIT_CARD])
 * @param gracePeriod Grace period of wallet (valid if wallet type is [WalletType.CREDIT_CARD])
 * @param color Number representation of some color
 * @param iconPath Path to icon for this wallet
 *
 * @author JustSpace
 */
data class Wallet (
	val walletId: Long = 0,
	val name: String,
	val accountId: Long,
	val balance: Double,
	val currency: String,
	val type: WalletType,
	val creditLimit: Double = 0.0,
	val interestRate: Double = 0.0,
	val paymentDay: Int = 0,
	val gracePeriod: Int = 0,
	val color: Int,
	val iconPath: String
) : BaseModel
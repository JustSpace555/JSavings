package ru.jsavings.domain.model.database.wallet

/**
 * Type of wallet
 *
 * @author JustSpace
 */
enum class WalletType {
	CASH,
	DEBIT_CARD,
	CREDIT_CARD,
	CRYPTO_CURRENCY,
	PRECIOUS_METALS,
	SECURITIES
}